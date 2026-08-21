import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from '@angular/forms';
import { finalize } from 'rxjs';
import {
  ApiService,
  Artist,
  Category,
  CreateArtistRequest,
  CreateCategoryRequest,
  CreateSongRequest,
  Song
} from './api.service';

@Component({
  selector: 'app-root',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly api = inject(ApiService);

  protected songs: Song[] = [];
  protected artists: Artist[] = [];
  protected categories: Category[] = [];
  protected loadingSongs = false;
  protected token = localStorage.getItem('spoti-token');
  protected statusMessage = 'Backend baglantisi hazirlaniyor...';
  protected errorMessage = '';

  protected readonly registerForm = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  protected readonly loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  protected readonly artistForm = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.minLength(2)]],
    country: [''],
    biography: [''],
    birthDate: ['']
  });

  protected readonly categoryForm = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.minLength(2)]],
    description: ['']
  });

  protected readonly songForm = this.fb.nonNullable.group({
    name: ['', Validators.required],
    duration: [0, [Validators.required, Validators.min(1)]],
    releaseDate: [''],
    active: [true],
    artistId: [0, [Validators.required, Validators.min(1)]],
    categoryId: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    this.refreshData();
    this.statusMessage = this.token
      ? 'Oturum hazir. Create endpointleri icin JWT eklenecek.'
      : 'Listeleme acik. Create islemleri icin kayit olup giris yapin.';
  }

  protected register(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.clearFeedback();
    this.api.register(this.registerForm.getRawValue()).subscribe({
      next: () => {
        this.statusMessage = 'Kayit basarili. Simdi giris yaparak token alabilirsiniz.';
        this.loginForm.patchValue({ email: this.registerForm.getRawValue().email });
        this.registerForm.reset();
      },
      error: (error) => this.handleError(error)
    });
  }

  protected login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.clearFeedback();
    this.api.login(this.loginForm.getRawValue()).subscribe({
      next: (response) => {
        localStorage.setItem('spoti-token', response.accessToken);
        this.token = response.accessToken;
        this.statusMessage = 'Giris basarili. Artik create endpointleri cagrilabilir.';
      },
      error: (error) => this.handleError(error)
    });
  }

  protected logout(): void {
    localStorage.removeItem('spoti-token');
    this.token = null;
    this.statusMessage = 'Oturum kapatildi. Yalnizca listeleme acik kalir.';
  }

  protected createArtist(): void {
    if (this.artistForm.invalid) {
      this.artistForm.markAllAsTouched();
      return;
    }

    const payload: CreateArtistRequest = {
      name: this.artistForm.getRawValue().name,
      country: this.artistForm.getRawValue().country,
      biography: this.artistForm.getRawValue().biography,
      birthDate: this.artistForm.getRawValue().birthDate || null
    };

    this.clearFeedback();
    this.api.createArtist(payload).subscribe({
      next: () => {
        this.statusMessage = 'Sanatci olusturuldu.';
        this.artistForm.reset({ name: '', country: '', biography: '', birthDate: '' });
        this.refreshArtists();
      },
      error: (error) => this.handleError(error)
    });
  }

  protected createCategory(): void {
    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    const payload: CreateCategoryRequest = {
      name: this.categoryForm.getRawValue().name,
      description: this.categoryForm.getRawValue().description
    };

    this.clearFeedback();
    this.api.createCategory(payload).subscribe({
      next: () => {
        this.statusMessage = 'Kategori olusturuldu.';
        this.categoryForm.reset({ name: '', description: '' });
        this.refreshCategories();
      },
      error: (error) => this.handleError(error)
    });
  }

  protected createSong(): void {
    if (this.songForm.invalid) {
      this.songForm.markAllAsTouched();
      return;
    }

    const formValue = this.songForm.getRawValue();
    const payload: CreateSongRequest = {
      name: formValue.name,
      duration: Number(formValue.duration),
      releaseDate: formValue.releaseDate || null,
      active: formValue.active,
      artistId: Number(formValue.artistId),
      categoryId: Number(formValue.categoryId)
    };

    this.clearFeedback();
    this.api.createSong(payload).subscribe({
      next: () => {
        this.statusMessage = 'Sarki olusturuldu ve liste guncellendi.';
        this.songForm.reset({
          name: '',
          duration: 0,
          releaseDate: '',
          active: true,
          artistId: 0,
          categoryId: 0
        });
        this.refreshSongs();
      },
      error: (error) => this.handleError(error)
    });
  }

  protected refreshAll(): void {
    this.refreshData();
  }

  protected trackBySongId(_: number, song: Song): number {
    return song.id;
  }

  private refreshData(): void {
    this.refreshSongs();
    this.refreshArtists();
    this.refreshCategories();
  }

  private refreshSongs(): void {
    this.loadingSongs = true;
    this.api
      .getSongs()
      .pipe(finalize(() => (this.loadingSongs = false)))
      .subscribe({
        next: (songs) => {
          this.songs = songs;
        },
        error: (error) => this.handleError(error)
      });
  }

  private refreshArtists(): void {
    this.api.getArtists().subscribe({
      next: (artists) => {
        this.artists = artists;
      },
      error: (error) => this.handleError(error)
    });
  }

  private refreshCategories(): void {
    this.api.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error) => this.handleError(error)
    });
  }

  private clearFeedback(): void {
    this.errorMessage = '';
  }

  protected isInvalid(control: AbstractControl | null): boolean {
    return !!control && control.invalid && (control.touched || control.dirty);
  }

  protected getControlError(control: AbstractControl | null): string {
    if (!control || !control.errors || (!control.touched && !control.dirty)) {
      return '';
    }

    return this.resolveErrorMessage(control.errors);
  }

  private resolveErrorMessage(errors: ValidationErrors): string {
    if (errors['required']) {
      return 'Bu alan zorunludur.';
    }

    if (errors['email']) {
      return 'Gecerli bir e-posta adresi girin.';
    }

    if (errors['minlength']) {
      return `En az ${errors['minlength'].requiredLength} karakter girin.`;
    }

    if (errors['min']) {
      return `Deger en az ${errors['min'].min} olmali.`;
    }

    return 'Gecersiz deger.';
  }

  private handleError(error: { error?: { message?: string } }): void {
    this.errorMessage = error.error?.message ?? 'Beklenmeyen bir hata olustu.';
  }
}
