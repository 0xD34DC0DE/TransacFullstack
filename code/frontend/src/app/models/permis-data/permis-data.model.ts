import { CategorieAge } from "../categorie-age.enum";
import { PermisType } from "../permis-type.enum";
import { ErrorMessage } from "../error-message/error-message.model";

export class PermisDataModel extends ErrorMessage {
  categorieAge: CategorieAge;
  typePermis: PermisType;
  nom: string;
  prenom: string;
  dateExpiration: Date;
  codeQRBase64: string;
}