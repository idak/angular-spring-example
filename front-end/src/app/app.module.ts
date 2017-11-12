import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import {RouterModule, Router} from "@angular/router";
import {appRoutes} from "./router";
import {FormsModule} from "@angular/forms";
import {AuthGuardGuard} from "./components/login/auth-guard.guard";
import { LoginService } from './services/login.service';
import { HttpModule } from '@angular/http';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthGuardGuard, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
