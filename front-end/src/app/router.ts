import {Routes} from "@angular/router";
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuardGuard} from "./components/login/auth-guard.guard";

export const  appRoutes: Routes = [
  {path:'auth', component: LoginComponent},
  {path:'home', component:HomeComponent, canActivate: [ AuthGuardGuard ] }
]
