import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthTokenService } from '../services/auth-token.service';

export const jwtInterceptor: HttpInterceptorFn = (request, next) => {
  const authTokenService = inject(AuthTokenService);
  const token = authTokenService.getToken();

  if (token) {
    const authenticatedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(authenticatedRequest);
  }

  return next(request);
};
