import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDataModel } from 'src/app/models/user-data/user-data.model';
import { ErrorsService } from 'src/app/services/errors.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  constructor(private router: Router, private userService: UserService, private error: ErrorsService) { }

  nas: string;

  loginForm = new FormGroup({
    login: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })

  loginObserver = {
    next: (result: UserDataModel) => this.nas = result.numeroAssuranceSocial,
    error: err => console.log(err),
    complete: () => this.handleResponse()
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if(this.loginForm.valid) {
      this.userService.login(this.loginForm.get('login').value, this.loginForm.get('password').value).subscribe(this.loginObserver);
    }
  }

  handleResponse() {
    if(!this.error.hasError) {
      sessionStorage.setItem('userNAS', this.nas);
      this.router.navigateByUrl("/dashboard");
    }
  }

  

}
