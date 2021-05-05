import { Component, Input, OnInit } from '@angular/core';
import { PermisDataModel } from 'src/app/models/permis-data/permis-data.model';
import { PermisType } from 'src/app/models/permis-type.enum';
import { PermisService } from 'src/app/services/permis.service';

@Component({
  selector: 'app-permit-management',
  templateUrl: './permit-management.component.html',
  styleUrls: ['./permit-management.component.css']
})
export class PermitManagementComponent implements OnInit {

  constructor(private permisService: PermisService) { }

  @Input() nas: string;
  permit: PermisDataModel;

  permitDataObserver = {
    next: data => this.permit = data,
    err: err => console.log(err),
    complete: () => {}
  }

  permitRenewDataObserver = {
    next: success => { if(success) { this.getPermitData(this.nas); } },
    err: err => console.log(err),
    complete: () => {}
  }

  ngOnInit(): void {
    if(this.nas != null) {
      this.getPermitData(this.nas)
    }
  }

  renew() {
    if(this.nas != null && this.permit.typePermis == PermisType.TEST) {
      this.permisService.renewPermis(this.nas, PermisType.TEST).subscribe(this.permitRenewDataObserver);
    }
  }

  getPermitData(nas: string) {
    if(nas != null) {
      this.permisService.getPermisFromNas(this.nas).subscribe(this.permitDataObserver);
    }
  }

}
