import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { MinisterUserDataModel } from '../models/minister-user-data/minister-user-data.model';
import { environment } from 'src/environments/environment';
import { CitoyenModel } from '../models/citoyen/citoyen.model';
import { UserDataModel } from '../models/user-data/user-data.model';
import { EnfantRegisterData } from '../models/enfant-register/enfant-register-data.modet';
import { CredentialModel } from '../models/credential/credential.model';
import { ErrorsService } from './errors.service';
import { ErrorMessage } from '../models/error-message/error-message.model';
import { NasList } from '../models/nas-list/nas-list.model';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  readonly URL_PREFIX: string = "user/"

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

  getUserMinisterData(nas: string): Observable<MinisterUserDataModel> {
    return this.http.get<MinisterUserDataModel>(environment.apiUrl + this.URL_PREFIX + `minister/citoyen/${nas}`).pipe(
        tap(this.errorMessageObserver),
        map((errMsg: ErrorMessage) => <MinisterUserDataModel>errMsg)
      );
  }

  registerCitoyen(citoyen: CitoyenModel): Observable<UserDataModel> {
    return this.http.post<UserDataModel>(environment.apiUrl + this.URL_PREFIX + 'register/citoyen', citoyen).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <UserDataModel>errMsg)
    );
  }

  registerEnfantCitoyen(parentNas: string, enfantNas: string): Observable<UserDataModel> {
    return this.http.post<UserDataModel>(environment.apiUrl + this.URL_PREFIX + 'register/enfant', new EnfantRegisterData(parentNas, enfantNas)).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <UserDataModel>errMsg)
    );
  }

  getCitoyenChildrensNas(citoyenNas: string): Observable<NasList> {
    return this.http.get<NasList>(environment.apiUrl + this.URL_PREFIX + `enfant/getChildrenOf/${citoyenNas}`);
  }

  login(login: string, password: string): Observable<UserDataModel> {
    return this.http.post<UserDataModel>(environment.apiUrl + this.URL_PREFIX + '/login', new CredentialModel(login, password)).pipe(
      tap(this.errorMessageObserver),
      map((errMsg: ErrorMessage) => <UserDataModel>errMsg)
    );
  }
}
