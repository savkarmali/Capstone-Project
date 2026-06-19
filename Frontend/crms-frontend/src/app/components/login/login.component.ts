import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service.js';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <div class="page" style="max-width:480px">
      <div class="panel">
        <h3>Admin Login</h3>
        <form [formGroup]="form" (ngSubmit)="login()">
          <label class="form-label mt-3">Email</label>
          <input class="form-control" formControlName="email">
          <label class="form-label mt-3">Password</label>
          <input class="form-control" type="password" formControlName="password">
          @if(error){ <div class="alert alert-danger mt-3">{{error}}</div> }
          <button class="btn btn-primary mt-4 w-100" [disabled]="form.invalid">Login</button>
        </form>
        <p class="small-muted mt-3">Use admin&#64;crms.com / admin123</p>
      </div>
    </div>
  `
})
export class LoginComponent {
  error = '';
  form = this.fb.group({ email: ['admin@crms.com', [Validators.required, Validators.email]], password: ['admin123', Validators.required] });
  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}
  login() {
    this.auth.login(this.form.value.email!, this.form.value.password!).subscribe({
      next: () => this.router.navigateByUrl('/dashboard'),
      error: (err:any) => this.error = err.error?.message || 'Login failed'
    });
  }
}
