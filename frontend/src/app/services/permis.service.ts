import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { PermisDataModel } from '../models/permis-data/permis-data.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { PermisType } from '../models/permis-type.enum';
import { PermisValidity } from '../models/permis-validity/permis-validity.model';
import { ErrorsService } from './errors.service';
import { ErrorMessage } from '../models/error-message/error-message.model';

@Injectable({
  providedIn: 'root'
})
export class PermisService {

  readonly URL_PREFIX: string = "permis/"

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

  getPermisFromNas(nas: string): Observable<PermisDataModel> {
    return this.http.get<PermisDataModel>(environment.apiUrl + this.URL_PREFIX + `get/${nas}`).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <PermisDataModel>errMsg)
    );
  }

  permisValidity(hash :string): Observable<PermisValidity> {
    return this.http.get<PermisValidity>(environment.apiUrl + this.URL_PREFIX + `verify/${hash}`).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <PermisValidity>errMsg)
    );
  }

  renewPermis(nas: string, type: PermisType): Observable<PermisDataModel> {
    return this.http.post<PermisDataModel>(environment.apiUrl + this.URL_PREFIX + `renew/${nas}?type=${type}`, null).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <PermisDataModel>errMsg)
    );;
  }
}
