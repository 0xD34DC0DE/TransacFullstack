import { Component, OnInit } from '@angular/core';
import { ErrorsService } from 'src/app/services/errors.service';

@Component({
  selector: 'app-error-message',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.css']
})
export class ErrorMessageComponent implements OnInit {

  errorMessage: string;
  subscription: any;

  constructor(private error: ErrorsService) { }

  ngOnInit(): void {
    this.subscription = this.error.getErrorEventEmitter().subscribe(errorMessage => this.errorMessage = errorMessage)
  }

}
