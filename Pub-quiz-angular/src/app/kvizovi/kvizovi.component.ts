import { KategorijaModel } from './../shared/kategorija-model';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { KvizoviService } from './../shared/kvizovi.service';
import { Component, OnInit } from '@angular/core';
import { KvizModel } from 'src/app/shared/kviz-model';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-kvizovi',
  templateUrl: './kvizovi.component.html',
  styleUrls: ['./kvizovi.component.css']
})
export class KvizoviComponent implements OnInit {

  username: string = "neprijavljen";

  kvizovi: Array<KvizModel> = [];

  p: number = 1;

  total: number;

  kategorije: Array<KategorijaModel> = [];

  odabranaKategorija: number = 1;

  form: FormGroup;

  constructor(private kvizoviServis: KvizoviService,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder) {

    this.username = activateRoute.snapshot.params.username;

  }

  ngOnInit(): void {
    this.kvizoviServis.getKategorije().subscribe(kategorije => this.kategorije = kategorije);
    this.getPage(1);
  }

  filtriraj() {
    this.getPage(1);
  }

  getPage(page: number) {
    this.kvizoviServis.getKvizove(page, this.username, this.odabranaKategorija).subscribe(pageResponse => {
      this.kvizovi = pageResponse.kvizovi;
      this.total = pageResponse.ukupno;
      this.p = page;
    });
  }

  otvoriKviz(id: number): void {
    this.router.navigateByUrl(this.username + '/kviz/' + id);
  }

}
