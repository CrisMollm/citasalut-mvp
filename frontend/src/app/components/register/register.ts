import { Component, inject } from '@angular/core';
import { AuthService} from '../../service/auth.service';
import { Router } from '@angular/router';
import {RegisterRequest} from '../../models/usuario.model';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  // Datos vinculados al formulario
  userData: RegisterRequest = {
    nombre: '',
    email: '',
    password: ''
  };


  loading = false;
  onSubmit() {

    this.loading = true;

    this.authService.register(this.userData).subscribe({

      next: (response) => {
        console.log('Usuari creat correctament');
        alert('Registre correcte! Ara pots fer login.');

        this.router.navigate(['/login']); // Tras registrar, vamos al login
      },
      error: (err) => {
        console.error('Error al crear usuari:', err);
        this.loading = false;
        alert('Error en el registre. Revisa si el correu ja existeix.');
      }
    });
  }
}
