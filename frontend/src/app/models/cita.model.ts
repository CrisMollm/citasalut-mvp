export interface Cita {
  id: number;
  dataHora: string;
  especialidad: string;
  nombreMedico: string;
  estat: 'PENDIENTE' | 'REALIZADA' | 'CANCELADA';
}

export interface CitaRequest {
  dataHora: string;
  especialidad: string;
  nombreMedico: string;
}
