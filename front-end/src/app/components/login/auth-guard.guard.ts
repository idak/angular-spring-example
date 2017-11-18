import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import { JwtHelper } from 'angular2-jwt';
import { Console } from '@angular/core/src/console';

@Injectable()
export class AuthGuardGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    let token = localStorage.getItem('token');
    let jwtHelper: JwtHelper = new JwtHelper();
    if(!token && jwtHelper.isTokenExpired(token)){
      this.router.navigate(['auth']);
      return false;
    }
    return true;
  }
}
