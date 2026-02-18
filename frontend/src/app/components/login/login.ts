import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone : false
})
export class LoginComponent {
  // Inyectamos las dependencias
  private authService = inject(AuthService);
  private router = inject(Router);

  // Datos del formulario
  credentials = {
    email: '',
    password: ''
  };

  onSubmit() {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        // Guardamos el token
        localStorage.setItem('token', response.token);
        localStorage.setItem('usuario_nombre', response.nombre);

        console.log('Login correcte! Token guardat.');

        this.router.navigate(['/citas']);
      },
      error: (err) => {
        console.error('Error de login', err);
        alert('Credencials incorrectes. Torna-ho a provar.');
      }
    });
  }
}
