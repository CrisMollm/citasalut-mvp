import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent} from './components/register/register';
import { authGuard} from './guards/auth.guards';
import {LoginComponent} from './components/login/login';

const routes: Routes = [
  { path: 'register', component: RegisterComponent }, // Página de registro
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/register', pathMatch: 'full' }, // Cambiamos el inicio a register
  { path: '**', redirectTo: '/register' }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

