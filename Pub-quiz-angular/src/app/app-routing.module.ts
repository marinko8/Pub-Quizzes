import { CreateKvizComponent } from './korisnicki-profil/create-kviz/create-kviz.component';
import { KorisnickiProfilComponent } from './korisnicki-profil/korisnicki-profil.component';
import { ViewKvizComponent } from './view-kviz/view-kviz.component';
import { KvizoviComponent } from './kvizovi/kvizovi.component';
import { HomeComponent } from './home/home.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent},
  { path: 'sign-up', component: SignUpComponent},
  { path: 'kvizovi/:page/:username', component: KvizoviComponent},
  { path: ':username/kviz/:id', component: ViewKvizComponent},
  { path: 'korisnik/:username', component: KorisnickiProfilComponent, canActivate: [AuthGuard] },
  { path: 'novi-kviz/:username', component: CreateKvizComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
