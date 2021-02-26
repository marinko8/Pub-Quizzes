import { KategorijaModel } from './kategorija-model';
export class KvizModel{
    id: number;
    autor: string;
    naziv: string;
    broj_pitanja: number;
    zbroj_ocjena_tezine: number;
    broj_ocjena_tezine: number;
    zbroj_ocjena: number;
    broj_ocjena: number;
    broj_rjesenja: number;
    kategorija: KategorijaModel;
}