import { Component, OnInit } from '@angular/core';
import { JwtHelper } from 'angular2-jwt'
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  username: string;
  
  constructor() { }

  ngOnInit() {
    let jwtHelper: JwtHelper = new JwtHelper();
    this.username = jwtHelper.decodeToken(localStorage.getItem('token')).sub;
  }

}
