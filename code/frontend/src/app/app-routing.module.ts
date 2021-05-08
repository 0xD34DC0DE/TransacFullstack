import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChildrenDashboardComponent } from './components/children-dashboard/children-dashboard.component';
import { ChildrenRegisterComponent } from './components/children-register/children-register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { StartRegisterComponent } from './components/start-register-form/start-register-form.component';
import { VerifyPermisComponent } from './components/verify-permis/verify-permis.component';

const routes: Routes = [
  { path: '', component: LoginFormComponent},
  { path: 'login', component: LoginFormComponent},
  { path: 'start-register', component: StartRegisterComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'childrenDashboard', component: ChildrenDashboardComponent},
  { path: 'childrenRegister', component: ChildrenRegisterComponent},
  { path: 'permis/verify/:hash', component: VerifyPermisComponent},
  { path: '404', component: NotFoundComponent},
  { path: '**', redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
