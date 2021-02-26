import { OdgovorRequestModel } from "./odgovor-request-model";

export class PitanjeRequestModel{
    naziv: string;
    odgovori: OdgovorRequestModel[]=[];
}