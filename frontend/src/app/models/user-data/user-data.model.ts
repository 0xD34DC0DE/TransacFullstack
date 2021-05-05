import { ErrorMessage } from "../error-message/error-message.model";

export class UserDataModel extends ErrorMessage{
  numeroAssuranceSocial: string;
  nom: string;
  prenom: string;
  sexe: string;
  age: number;
  courriel: string;
  numeroTelephone: string;
  villeResidence: string;
}
