import { Component, Input, OnInit } from '@angular/core';
import { EmailSendResult } from 'src/app/models/email-send-result/email-send-result.model';
import { PermisDataModel } from 'src/app/models/permis-data/permis-data.model';
import { PermisType } from 'src/app/models/permis-type.enum';
import { ErrorsService } from 'src/app/services/errors.service';
import { MailService } from 'src/app/services/mail.service';
import { PermisService } from 'src/app/services/permis.service';

@Component({
  selector: 'app-permit-management',
  templateUrl: './permit-management.component.html',
  styleUrls: ['./permit-management.component.css']
})
export class PermitManagementComponent implements OnInit {

  constructor(private permisService: PermisService, private error: ErrorsService, private mail: MailService) { }

  @Input() nas: string;
  permit: PermisDataModel;

  emailResult: boolean = null;

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

  emailSendObserver = {
    next: (result: EmailSendResult) => { this.emailResult = result.successful; debugger; },
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

  sendImage() {
    this.mail.sendPermitAsImage(this.nas).subscribe(this.emailSendObserver);
  }

  sendPdf() {
    this.mail.sendPermitAsPdf(this.nas).subscribe(this.emailSendObserver);
  }

}
