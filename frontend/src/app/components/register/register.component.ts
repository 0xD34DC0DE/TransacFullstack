import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CitoyenModel } from 'src/app/models/citoyen/citoyen.model';
import { MinisterUserDataModel } from 'src/app/models/minister-user-data/minister-user-data.model';
import { UserDataModel } from 'src/app/models/user-data/user-data.model';
import { ErrorsService } from 'src/app/services/errors.service';
import { PermisService } from 'src/app/services/permis.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private router: Router, private userService: UserService, private error: ErrorsService) { }

  userData: MinisterUserDataModel;
  responseData: UserDataModel;
  registrationData: CitoyenModel = new CitoyenModel();
  phoneNumberRegex: string = '[- +()0-9]+';

  formValidations = {
    'numeroTelephone': ['pattern', 'required'],
    'courriel': ['email', 'required']
  }

  validationMessages = {
    'required': 'field is required',
    'email': 'field is invalid',
    'pattern': 'field is invalid'
  }

  userDataObserver = {
    next: userData => this.responseData = userData,
    error: err => console.log(err),
    complete: () => this.handleRegisteringResponse()
  }

  ngOnInit(): void {
    if(history.state != null) {
      this.userData = history.state;
      console.log(this.userData);

      this.registrationData.numeroAssuranceSocial = this.userData.numeroAssuranceSocial;
    }
  }

  onSubmit(): void {
    if(this.registerForm.valid) {
      this.registrationData.courriel = this.courriel.value;
      this.registrationData.numeroTelephone = this.numeroTelephone.value;
      this.registrationData.villeResidence = this.villeResidence.value;
      this.registrationData.login = this.login.value;
      this.registrationData.password = this.password.value;

      this.userService.registerCitoyen(this.registrationData).subscribe(this.userDataObserver)
    } else {
      this.handleInvalidForm();
    }
  }

  registerForm: FormGroup = new FormGroup({
    courriel: new FormControl('', [Validators.email, Validators.required]),
    numeroTelephone: new FormControl('', [Validators.pattern(this.phoneNumberRegex), Validators.required]),
    villeResidence: new FormControl('', [Validators.required]),
    login: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  handleRegisteringResponse() {
    if(!this.error.hasError) {
      this.endRegistering();
    }
  }

  endRegistering() {
    sessionStorage.setItem("userNAS", this.userData.numeroAssuranceSocial);
    this.router.navigateByUrl("/dashboard");
  }

  handleInvalidForm() {
    let errorMessage: string = '';

    debugger;

    for (let controlName in this.formValidations) {
      let validatorNames: string[] = this.formValidations[controlName];

      for (let validatorName of validatorNames) {
        if(this.registerForm.get(controlName).hasError(validatorName)) {
          console.log(controlName);
          errorMessage += `${controlName} ${this.validationMessages[validatorName]}\n`;
        }
      }
    }

    if(errorMessage != '') {
      this.error.setNewError(errorMessage);
    }
  }

  get courriel() {
    return this.registerForm.get('courriel');
  }

  get numeroTelephone() {
    return this.registerForm.get('numeroTelephone');
  }

  get villeResidence() {
    return this.registerForm.get('villeResidence');
  }

  get login() {
    return this.registerForm.get('login');
  }

  get password() {
    return this.registerForm.get('password');
  }

}
