import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
}

export interface Artist {
  id: number;
  name: string;
  country: string | null;
  biography: string | null;
  birthDate: string | null;
}

export interface Category {
  id: number;
  name: string;
  description: string | null;
}

export interface Song {
  id: number;
  name: string;
  duration: number;
  releaseDate: string | null;
  active: boolean;
  artist: {
    id: number;
    name: string;
  };
  category: {
    id: number;
    name: string;
  };
}

export interface CreateArtistRequest {
  name: string;
  country: string;
  biography: string;
  birthDate: string | null;
}

export interface CreateCategoryRequest {
  name: string;
  description: string;
}

export interface CreateSongRequest {
  name: string;
  duration: number;
  releaseDate: string | null;
  active: boolean;
  artistId: number;
  categoryId: number;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

  register(payload: RegisterRequest): Observable<unknown> {
    return this.http.post(`${this.baseUrl}/auth/register`, payload);
  }

  login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/auth/login`, payload);
  }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>(`${this.baseUrl}/songs/getAll`);
  }

  getArtists(): Observable<Artist[]> {
    return this.http.get<Artist[]>(`${this.baseUrl}/artists/getAll`);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories/getAll`);
  }

  createArtist(payload: CreateArtistRequest): Observable<Artist> {
    return this.http.post<Artist>(`${this.baseUrl}/artists/create`, payload);
  }

  createCategory(payload: CreateCategoryRequest): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/categories/create`, payload);
  }

  createSong(payload: CreateSongRequest): Observable<Song> {
    return this.http.post<Song>(`${this.baseUrl}/songs/create`, payload);
  }
}
