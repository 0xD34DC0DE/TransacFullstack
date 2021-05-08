import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StartRegisterComponent } from './components/start-register-form/start-register-form.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { PermitManagementComponent } from './components/permit-management/permit-management.component';
import { ChildrenDashboardComponent } from './components/children-dashboard/children-dashboard.component';
import { ChildrenRegisterComponent } from './components/children-register/children-register.component';
import { ErrorMessageComponent } from './components/error-message/error-message.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { VerifyPermisComponent } from './components/verify-permis/verify-permis.component';




@NgModule({
  declarations: [
    AppComponent,
    StartRegisterComponent,
    LoginFormComponent,
    RegisterComponent,
    DashboardComponent,
    NavigationComponent,
    PermitManagementComponent,
    ChildrenDashboardComponent,
    ChildrenRegisterComponent,
    ErrorMessageComponent,
    NotFoundComponent,
    VerifyPermisComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
