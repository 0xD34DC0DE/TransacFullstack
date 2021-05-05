import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PermisValidity } from 'src/app/models/permis-validity/permis-validity.model';
import { PermisService } from 'src/app/services/permis.service';

@Component({
  selector: 'app-verify-permis',
  templateUrl: './verify-permis.component.html',
  styleUrls: ['./verify-permis.component.css']
})
export class VerifyPermisComponent implements OnInit {

  valid: boolean = null;

  constructor(private router: Router, private permisService: PermisService) { }

  verifyObserver = {
    next: (result: PermisValidity) => this.valid = result.valid,
    error: err => console.log(err),
    complete: () => {}
  }

  ngOnInit(): void {
    this.permisService.permisValidity(this.getHash()).subscribe(this.verifyObserver);
  }

  getHash(): string {
    let splits = this.router.url.split('/');
    return splits[splits.length - 1];
  }

}
