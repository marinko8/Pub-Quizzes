import { KvizRequestModel } from './kviz-request-model';
import { PregledKorisnikaModel } from './pregled-korisnika';
import { PageResponse } from './page-response';
import { KategorijaModel } from './kategorija-model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KvizModel } from 'src/app/shared/kviz-model';
import { PitanjeOdgovoriModel } from './pitanje-odgovori';
import { RezultatModel } from './rezultat-model';

@Injectable({
  providedIn: 'root'
})
export class KvizoviService {

  ROOT_URL: string="http://localhost:8080";

  constructor(private http: HttpClient) { }

  getKvizove(page: number, username?: string, kategorija?: number): Observable<PageResponse> {
    if (username !== undefined && username !== 'neprijavljen' && kategorija !== 1) {
      return this.http.get<PageResponse>(this.ROOT_URL+'/api/kviz/page/?brojStranice=' + page + '&username=' + username + '&kategorija=' + kategorija);
    } else if (username !== undefined && username !== 'neprijavljen') {
      return this.http.get<PageResponse>(this.ROOT_URL+'/api/kviz/page/?brojStranice=' + page + '&username=' + username);
    } else if (kategorija !== 1) {
      return this.http.get<PageResponse>(this.ROOT_URL+'/api/kviz/page/?brojStranice=' + page + '&kategorija=' + kategorija);
    } else {
      return this.http.get<PageResponse>(this.ROOT_URL+'/api/kviz/page/?brojStranice=' + page);
    }
  }

  getKategorije(): Observable<Array<KategorijaModel>> {
    return this.http.get<Array<KategorijaModel>>(this.ROOT_URL+'/api/kviz/kategorije');
  }

  getPitanja(id: number): Observable<Array<PitanjeOdgovoriModel>> {
    return this.http.get<Array<PitanjeOdgovoriModel>>(this.ROOT_URL+'/api/kviz/pitanja/?id=' + id);
  }
  getKviz(id: number): Observable<KvizModel> {
    return this.http.get<KvizModel>(this.ROOT_URL+'/api/kviz?id=' + id);
  }

  saveRezultat(rezultat: RezultatModel): any {
    return this.http.post(this.ROOT_URL+'/api/kviz/spremiRezultat', rezultat).subscribe();
  }

  saveNoviKviz(kviz: KvizRequestModel): any {
    this.http.post(this.ROOT_URL+'/api/kviz/noviKviz', kviz).subscribe();
  }

  getPregledKorisnika(username: string): Observable<PregledKorisnikaModel> {
    return this.http.get<PregledKorisnikaModel>(this.ROOT_URL+'/api/kviz/korisnik/?username=' + username);
  }

  getKvizoveAutora(username: string, page: number): Observable<PageResponse> {
    return this.http.get<PageResponse>(this.ROOT_URL+'/api/kviz/kvizoviKorisnikaPage/?brojStranice=' + page + '&username=' + username);
  }

  obrisiKviz(id: number): any {
    this.http.delete(this.ROOT_URL+'/api/kviz/obrisiKviz/?id=' + id).subscribe();
  }
}
