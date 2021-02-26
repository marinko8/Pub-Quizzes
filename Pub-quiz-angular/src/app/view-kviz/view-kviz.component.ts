import { MatDialog, MatDialogConfig, MatDialogModule } from '@angular/material/dialog';
import { RezultatModel } from './../shared/rezultat-model';
import { PitanjeOdgovoriModel } from './../shared/pitanje-odgovori';
import { OdgovorModel } from './../shared/odgovor-model';
import { map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { PitanjeModel } from '../shared/pitanje-model';
import { KvizoviService } from '../shared/kvizovi.service';
import { LocalStorageService } from 'ngx-webstorage';
import { ActivatedRoute, Router } from '@angular/router';
import { throwError } from 'rxjs';
import { KvizModel } from '../shared/kviz-model';
import { RezultatiDialogComponent } from '../dialogs/rezultati-dialog/rezultati-dialog.component';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-view-kviz',
  templateUrl: './view-kviz.component.html',
  styleUrls: ['./view-kviz.component.css'],
  animations: [
    trigger('fade',[
      transition('* => void', [
        animate(500, style({backgroundColor: 'blue',opacity:0}))
      ])
    ])
  ]
})
export class ViewKvizComponent implements OnInit {

  id: number;
  username: string = "neprijavljen";
  odgovoreno: boolean=false;
  krivoOdgovoreno: boolean=false;
  kviz: KvizModel;
  pitanjaOdgovori: Array<PitanjeOdgovoriModel> = new Array<PitanjeOdgovoriModel>();
  trenutnoPitanje: PitanjeOdgovoriModel;
  indexPitanja: number = 0;
  odabraniOdgovor: OdgovorModel;
  bodovi: number = 0;
  rezultat: RezultatModel = new RezultatModel;
  ukupnoBodovi: number=0;

  constructor(private kvizoviServis: KvizoviService,
    private localStorage: LocalStorageService,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog) {
    this.id = activateRoute.snapshot.params.id;
    this.username = activateRoute.snapshot.params.username;
  }

  ngOnInit(): void {
    this.kvizoviServis.getPitanja(this.id).subscribe(pitanja => {
      this.pitanjaOdgovori = pitanja;
      this.trenutnoPitanje = this.pitanjaOdgovori[this.indexPitanja]
    }, error => {
      throwError(error);
    });
    this.kvizoviServis.getKviz(this.id).subscribe(kviz => this.kviz = kviz);
  }

  odgovori(id: number): void {
    this.indexPitanja++;
    if (this.indexPitanja === this.pitanjaOdgovori.length) {
      this.odabraniOdgovor = this.trenutnoPitanje.odgovori.find(odgovor => { return odgovor.id === id });
      if (this.odabraniOdgovor.tocno) {
        this.bodovi += (this.kviz.zbroj_ocjena_tezine/this.kviz.broj_ocjena_tezine) * 2;
      } else {
        this.krivoOdgovoreno=true;
        this.bodovi -= 1;
      }
      this.odgovoreno=true;
      setTimeout(()=>{
        this.krivoOdgovoreno=false;
        this.odgovoreno=false;
      }, 500);
      this.rezultat.idKviza = this.id;
      this.rezultat.username = this.username;
      this.rezultat.bodovi = this.bodovi;
      this.ukupnoBodovi=this.kviz.broj_pitanja*((this.kviz.zbroj_ocjena_tezine/this.kviz.broj_ocjena_tezine) * 2);
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {rezultat: this.rezultat, ukupnoBodova: this.ukupnoBodovi};
    
      const dialogRef = this.dialog.open(RezultatiDialogComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(result=>{
        this.rezultat.tezina=result.tezina;
        this.rezultat.ocjena=result.ocjena;
        this.kvizoviServis.saveRezultat(this.rezultat);
        setTimeout(()=>{
          this.router.navigateByUrl('/kvizovi/1/' + this.username);
        }, 1000);
      });

    } else {
      this.odabraniOdgovor = this.trenutnoPitanje.odgovori.find(odgovor => { return odgovor.id === id });
      if (this.odabraniOdgovor.tocno) {
        this.bodovi += (this.kviz.zbroj_ocjena_tezine/this.kviz.broj_ocjena_tezine) * 2;
      } else {
        this.krivoOdgovoreno=true;
        this.bodovi -= 1;
      }
      this.odgovoreno=true;
      setTimeout(()=>{
        this.krivoOdgovoreno=false;
        this.odgovoreno=false;
        this.trenutnoPitanje = this.pitanjaOdgovori[this.indexPitanja];
      }, 700);
    }
  }


}
