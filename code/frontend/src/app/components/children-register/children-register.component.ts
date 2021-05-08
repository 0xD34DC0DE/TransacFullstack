import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { numericMaxLength } from 'src/app/directives/numeric-max-length.directive';
import { numericMinLength } from 'src/app/directives/numeric-min-length.directive';
import { MinisterUserDataModel } from 'src/app/models/minister-user-data/minister-user-data.model';
import { UserDataModel } from 'src/app/models/user-data/user-data.model';
import { ErrorsService } from 'src/app/services/errors.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-children-register',
  templateUrl: './children-register.component.html',
  styleUrls: ['./children-register.component.css']
})
export class ChildrenRegisterComponent implements OnInit {

  constructor(private router: Router, private userService: UserService, private error: ErrorsService) { }

  userData: MinisterUserDataModel;
  registrationResult: UserDataModel;

  formValidations = {
    'nas': ['required', 'minlength', 'maxlength']
  }

  validationMessages = {
    'required': 'field is required',
    'minlength': 'should be 9 digits',
    'maxlength': 'should be 9 digits',
  }

  ngOnInit(): void {
  }

  enfantRegistrationObserver = {
    next: result => this.registrationResult = result,
    error: err => console.log(err),
    complete: () => this.handleResponse()
  }

  registerForm = new FormGroup({
    nas: new FormControl('nas', [Validators.required, numericMinLength(9), numericMaxLength(9)])
  });  

  onSubmit(): void {
    if(this.registerForm.valid) {
      this.userService.registerEnfantCitoyen(this.userNas, this.registerForm.controls['nas'].value).subscribe(this.enfantRegistrationObserver);
    } else {
      this.handleInvalidForm();
    }
  }

  finnishRegistration() {
    this.registerForm.reset();
    this.router.navigateByUrl('/childrenDashboard');
  }

  goBack() {
    this.finnishRegistration();
  }

  handleResponse() {
    if(!this.error.hasError) {
      this.finnishRegistration();
    }
  }

  handleInvalidForm() {
    let errorMessage: string = '';

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

  get nas() {
    return this.registerForm.get('nas');
  }

  get userNas() {
    return sessionStorage.getItem("userNAS");;
  }

}
