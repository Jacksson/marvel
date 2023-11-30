import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Obtengo el token de autenticación (username y password)
    const token = btoa('user:password'); //En un entorno real usaria JWT o OAUTH 2

    // Clono la solicitud y agrego el encabezado de autorización
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Basic ${token}`
      }
    });

    // Continuar con la solicitud modificada
    return next.handle(authReq);
  }
}
