import { PitanjeRequestModel } from "./pitanje-request-model";

export class KvizRequestModel{
    username: string;
    naziv: string;
    kategorija: string;
    pitanja: Array<PitanjeRequestModel>=[];
}