import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter, Routes } from '@angular/router';
import { AppComponent } from './app/app.component';
import { LoginComponent } from './app/components/login/login.component';
import { DashboardComponent } from './app/components/dashboard/dashboard.component';
import { SurveyListComponent } from './app/components/survey-list/survey-list.component';
import { SurveyFormComponent } from './app/components/survey-form/survey-form.component';
import { ResponseListComponent } from './app/components/response-list/response-list.component';
import { PublicSurveyComponent } from './app/components/public-survey/public-survey.component';
import { authGuard } from './app/services/auth.guard.service';
import { tokenInterceptor } from './app/services/token.interceptor.service';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'surveys', component: SurveyListComponent, canActivate: [authGuard] },
  { path: 'surveys/new', component: SurveyFormComponent, canActivate: [authGuard] },
  { path: 'responses', component: ResponseListComponent, canActivate: [authGuard] },
  { path: 'survey/:token', component: PublicSurveyComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];

bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes), provideHttpClient(withInterceptors([tokenInterceptor]))]
});