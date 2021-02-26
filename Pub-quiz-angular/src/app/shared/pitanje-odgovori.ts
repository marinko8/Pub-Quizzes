import { OdgovorModel } from './odgovor-model';
import { PitanjeModel } from './pitanje-model';
export class PitanjeOdgovoriModel{
    pitanje: PitanjeModel;
    odgovori: Array<OdgovorModel>;
}