import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { EmailSendResult } from '../models/email-send-result/email-send-result.model';
import { ErrorMessage } from '../models/error-message/error-message.model';
import { PermisDataModel } from '../models/permis-data/permis-data.model';
import { PermisType } from '../models/permis-type.enum';
import { PermisValidity } from '../models/permis-validity/permis-validity.model';
import { ErrorsService } from './errors.service';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  readonly URL_PREFIX: string = "email/"

  constructor(private http: HttpClient, private error: ErrorsService) { }

  errorMessageObserver = {
    next: (message: ErrorMessage) => {
      if(message.errorMessage) {
        this.error.setNewError(message.errorMessage)
      } else {
        this.error.reset();
      }
    },
    error: err => this.error.setNewError("Server error"),
    complete: () => {}
  }

  sendPermitAsImage(nas: string): Observable<EmailSendResult> {
    return this.http.get<EmailSendResult>(environment.apiUrl + this.URL_PREFIX + `send/image/${nas}`).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <EmailSendResult>errMsg)
    );
  }

  sendPermitAsPdf(nas: string): Observable<EmailSendResult> {
    return this.http.get<EmailSendResult>(environment.apiUrl + this.URL_PREFIX + `send/pdf/${nas}`).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <EmailSendResult>errMsg)
    );
  }
}
