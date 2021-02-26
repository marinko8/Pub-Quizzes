import { AuthService } from './../auth/shared/auth.service';
import { KvizModel } from './../shared/kviz-model';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { KvizoviService } from '../shared/kvizovi.service';

@Component({
  selector: 'app-korisnicki-profil',
  templateUrl: './korisnicki-profil.component.html',
  styleUrls: ['./korisnicki-profil.component.css']
})
export class KorisnickiProfilComponent implements OnInit {

  kvizovi: Array<KvizModel> = [];
  username: string;
  bodovi: number = 0;
  brojOdigranihKvizova: number = 0;
  brojKreiranihKvizova: number = 0;
  p: number = 1;
  total: number;

  constructor(private kvizoviServis: KvizoviService,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService) {
    this.username = activateRoute.snapshot.params.username;
  }

  ngOnInit(): void {
    this.kvizoviServis.getPregledKorisnika(this.username).subscribe(response => {
      this.bodovi = response.bodovi;
      this.brojOdigranihKvizova = response.brojOdigranihKvizova;
      this.brojKreiranihKvizova = response.brojKreiranihKvizova;
    });
    this.getPage(1);
  }

  noviKviz(): void {
    console.log(this.username);
    this.router.navigateByUrl('/novi-kviz/' + this.username);
  }

  obrisiKviz(id: number): void {
    this.kvizoviServis.obrisiKviz(id);
    setTimeout(()=>{
      location.reload();
    }, 1000);
  }

  odjava(): void {
    this.authService.logout();
    this.router.navigateByUrl('');
  }

  otvoriKvizove(): void {
    this.router.navigateByUrl('/kvizovi/1/' + this.username);
  }

  getPage(page: number) {
    this.kvizoviServis.getKvizoveAutora(this.username, page).subscribe(pageResponse => {
      this.kvizovi = pageResponse.kvizovi;
      this.total = pageResponse.ukupno;
      this.p = page;
    });
  }

}
