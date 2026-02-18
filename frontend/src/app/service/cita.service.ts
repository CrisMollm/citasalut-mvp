import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cita } from '../models/cita.model';
import {API_CONFIG} from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class CitaService {
  private http = inject(HttpClient);
  private apiUrl = `${API_CONFIG.baseUrl}/citass`;

  //METODOS COMPONENTE MISCITAS

  //GET
  misCitas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(this.apiUrl);
  }

  //POST
  cancelarCita(id: number): Observable<Cita> {
    return this.http.put<Cita>(`${this.apiUrl}/${id}/cancelar`, {});
  }
}
