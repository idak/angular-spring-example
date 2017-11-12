import { Component, OnInit } from '@angular/core';
import {User} from "../../user";
import {Router} from "@angular/router";
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  errors: string;
  user: User = {username: '', password: ''};
  userToken: String;
  constructor(private router: Router, private loginService: LoginService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.loginService.authenaticate(this.user)
    .subscribe(res => {
      localStorage.setItem('token', res.headers.get('authorisation'));
      this.router.navigate(['']);
    },
        err => {
          console.log(err);
          this.errors = err.json().message;
        }
     );
  }

}
