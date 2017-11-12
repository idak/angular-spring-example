import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

const API_URL: String = "http://localhost:8080/api"

@Injectable()
export class LoginService {

  constructor(private http: Http) { }

  authenaticate(user) {
   return this.http.post('http://localhost:8080/api/auth', user);
  }

}
