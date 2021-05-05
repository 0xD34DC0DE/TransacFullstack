import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PermisDataModel } from 'src/app/models/permis-data/permis-data.model';
import { UserDataModel } from 'src/app/models/user-data/user-data.model';
import { PermisService } from 'src/app/services/permis.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private router: Router, private userService: UserService, private permisService: PermisService) { }

  userData: UserDataModel;
  permisData: PermisDataModel;

  userDataObserver = {
    next: userData => this.userData = userData,
    error: err => console.log(err),
    complete: () => {console.log(this.permisData)}
  };

  permisDataObserver = {
    next: permisData => this.permisData = permisData,
    error: err => console.log(err),
    complete: () => {}
  };

  ngOnInit(): void {
    if(sessionStorage.getItem("userNAS") != null) {
      this.userService.getUserMinisterData(sessionStorage.getItem("userNAS")).subscribe(this.userDataObserver);
      this.permisService.getPermisFromNas(sessionStorage.getItem("userNAS")).subscribe(this.permisDataObserver);
    }
  }

  logout() {
    sessionStorage.removeItem('userNAS');
    this.router.navigateByUrl('/login');
  }

}
