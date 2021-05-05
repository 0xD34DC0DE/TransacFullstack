import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NasList } from 'src/app/models/nas-list/nas-list.model';
import { UserDataModel } from 'src/app/models/user-data/user-data.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-children-dashboard',
  templateUrl: './children-dashboard.component.html',
  styleUrls: ['./children-dashboard.component.css']
})
export class ChildrenDashboardComponent implements OnInit {

  constructor(private router: Router, private userService: UserService)
  {}

  userData: UserDataModel;
  childrensNas: NasList; 

  userDataObserver = {
    next: userData => this.userData = userData,
    error: err => console.log(err),
    complete: () => {}
  };

  userChildrensDataObserver = {
    next: childrenNas => this.childrensNas = childrenNas,
    error: err => console.log(err),
    complete: () => {}
  };

  ngOnInit(): void {
    if(sessionStorage.getItem("userNAS") != null) {
      this.userService.getUserMinisterData(sessionStorage.getItem("userNAS")).subscribe(this.userDataObserver);
      this.userService.getCitoyenChildrensNas(sessionStorage.getItem("userNAS")).subscribe(this.userChildrensDataObserver);
    }
  }

  get userNas() {
    return sessionStorage.getItem("userNAS");
  }

}
