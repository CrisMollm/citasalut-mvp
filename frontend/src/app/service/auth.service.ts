import {Injectable, inject} from '@angular/core';
import {HttpClient, provideHttpClient} from '@angular/common/http';
import {LoginRequest, AuthResponse, RegisterRequest} from '../models/usuario.model';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import { API_CONFIG} from '../config/api.config';

@Injectable({providedIn: 'root'})
export class AuthService {

  private http = inject(HttpClient);
  private apiUrl = `${API_CONFIG.baseUrl}/auth`;

  private currentUserSubject = new BehaviorSubject<String | null>(localStorage.getItem('token'));
  public currentUser$ = this.currentUserSubject.asObservable();

  //METODO REGSTSRO
  register(userData : RegisterRequest) {
    return this.http.post<RegisterRequest>(`${this.apiUrl}/register`, userData);
  }


  //METODO LOGIN
  login(credential : LoginRequest) {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credential).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        this.currentUserSubject.next(response.token);
      })
    );
  }

  //METODO AUXILIAR
  isLoggedIn(): boolean{
    return !!localStorage.getItem('token');
  }
}

