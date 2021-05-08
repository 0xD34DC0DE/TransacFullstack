import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { numericMinLength } from 'src/app/directives/numeric-min-length.directive';
import { numericMaxLength } from 'src/app/directives/numeric-max-length.directive';
import { MinisterUserDataModel } from 'src/app/models/minister-user-data/minister-user-data.model';
import { UserService } from 'src/app/services/user.service';
import { ErrorsService } from 'src/app/services/errors.service';

@Component({
  selector: 'app-start-register',
  templateUrl: './start-register-form.component.html',
  styleUrls: ['./start-register-form.component.css']
})
export class StartRegisterComponent implements OnInit {

  constructor(private router: Router, private userService: UserService, private error: ErrorsService) {}

  userData: MinisterUserDataModel;

  ngOnInit(): void {
  }

  userDataObserver = {
    next: userData => this.userData = userData,
    error: err => console.log(err),
    complete: () => this.handleResponse()
  }

  onSubmit(): void {
    if(this.registerStartForm.valid) {
      this.userService.getUserMinisterData(this.registerStartForm.controls['nas'].value).subscribe(this.userDataObserver)
    }
  }

  registerStartForm = new FormGroup({
    nas: new FormControl('nas', [Validators.required, numericMinLength(9), numericMaxLength(9)])
  });

  handleResponse() {
    if(!this.error.hasError) {
      this.registerStartForm.reset();
      this.startRegistering();
    }
  }

  startRegistering() {
    this.router.navigateByUrl("/register", {state: this.userData});
  }

  get nas() {
    return this.registerStartForm.get('nas');
  }

}
