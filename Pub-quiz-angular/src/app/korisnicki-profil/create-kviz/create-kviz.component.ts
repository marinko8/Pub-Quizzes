import { OdgovorRequestModel } from './../../shared/odgovor-request-model';
import { PitanjeRequestModel } from './../../shared/pitanje-request-model';
import { KvizRequestModel } from './../../shared/kviz-request-model';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { KvizoviService } from 'src/app/shared/kvizovi.service';
import { ActivatedRoute, Router } from '@angular/router';
import { KategorijaModel } from 'src/app/shared/kategorija-model';

@Component({
  selector: 'app-create-kviz',
  templateUrl: './create-kviz.component.html',
  styleUrls: ['./create-kviz.component.css']
})
export class CreateKvizComponent implements OnInit {

  username: string;
  kvizForm: FormGroup;
  kviz: KvizRequestModel = new KvizRequestModel();
  kategorije: Array<KategorijaModel> = [];
  odabranaKategorija: string = "Sve kategorije";

  constructor(private fb: FormBuilder,
    private kvizoviServis: KvizoviService,
    private activateRoute: ActivatedRoute,
    private router: Router) {
    this.username = activateRoute.snapshot.params.username;
  }

  ngOnInit(): void {
    this.kvizoviServis.getKategorije().subscribe(kategorije => this.kategorije = kategorije);

    this.kvizForm = this.fb.group({
      nazivKviza: new FormControl('', Validators.required),
      pitanja: this.fb.array([this.fb.group({
        nazivPitanja: new FormControl('', Validators.required),
        odgovori: this.fb.array([this.fb.group({
          nazivOdgovora: new FormControl('', Validators.required),
          tocno: true
        })])
      })])
    })
  }

  get getPitanja() {
    return this.kvizForm.get('pitanja') as FormArray;
  }

  getOdgovori(element) {
    return element.get('odgovori') as FormArray;
  }

  addPitanje() {
    this.getPitanja.push(this.fb.group({
      nazivPitanja: new FormControl('', Validators.required),
      odgovori: this.fb.array([this.fb.group({
        nazivOdgovora: new FormControl('', Validators.required),
        tocno: true
      })])
    }));
  }

  removePitanje(index: number) {
    if (this.getPitanja.length > 1) {
      this.getPitanja.removeAt(index);
    }
  }

  addOdgovor(control: FormArray) {
    control.push(this.fb.group({
      nazivOdgovora: new FormControl('', Validators.required),
      tocno: false
    }));
  }

  removeOdgovor(control: FormArray, indexOdgovora) {
    if (control.length > 1) {
      if (control.controls[indexOdgovora].get('tocno').value == true) {
        control.removeAt(indexOdgovora);
        control.at(0).patchValue({
          tocno: true
        })
      } else {
        control.removeAt(indexOdgovora);
      }
    }
  }

  tocno(control: FormArray, indexOdgovora) {
    control.controls.forEach((element) => {
      element.patchValue({
        tocno: false
      })
    });
    control.at(indexOdgovora).patchValue({
      tocno: true
    });
  }

  submit(): void {
    if (this.kvizForm.valid) {
      this.kviz.username = this.username;
      this.kviz.naziv = this.kvizForm.get('nazivKviza').value;
      this.kviz.kategorija = this.odabranaKategorija;

      this.getPitanja.controls.forEach(elementPitanje => {
        let pitanje = new PitanjeRequestModel();
        pitanje.naziv = elementPitanje.get('nazivPitanja').value;
        this.getOdgovori(elementPitanje).controls.forEach(elementOdgovor => {
          let odgovor = new OdgovorRequestModel();
          odgovor.naziv = elementOdgovor.get('nazivOdgovora').value;
          odgovor.tocan = elementOdgovor.get('tocno').value;
          pitanje.odgovori.push(odgovor);
        });
        this.kviz.pitanja.push(pitanje);
      });

      this.kvizoviServis.saveNoviKviz(this.kviz);
      setTimeout(()=>{
        this.router.navigateByUrl('/korisnik/' + this.username);
      }, 1000);
    }
  }

  odbaci(): void {
    this.router.navigateByUrl('/korisnik/' + this.username);
  }

}
