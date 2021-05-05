import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorsService {

  constructor() { }

  errorEventEmitter: EventEmitter<string> = new EventEmitter();

  errorMessage: string = '';

  public setNewError(errorMessage: string): void {
    this.errorMessage = errorMessage;
    this.errorEventEmitter.emit(errorMessage);
  }

  public reset(): void {
    this.errorMessage = '';
    this.errorEventEmitter.emit('')
  }

  getErrorEventEmitter(): EventEmitter<string> {
    return this.errorEventEmitter;
  }

  get hasError(): boolean {
    return this.errorMessage != ''
  }

}
