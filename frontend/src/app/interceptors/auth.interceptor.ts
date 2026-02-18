import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Intentamos obtener el token del almacenamiento local
  const token = localStorage.getItem('token');

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    // Enviamos la peticion con el token
    return next(cloned);
  }

  //por si es una peticion sin token ej:Registro
  return next(req);
};
