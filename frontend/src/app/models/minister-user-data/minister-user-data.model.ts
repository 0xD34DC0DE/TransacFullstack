import { ErrorMessage } from "../error-message/error-message.model";
import { PermisType } from "../permis-type.enum";

export class MinisterUserDataModel extends ErrorMessage {
    numeroAssuranceSocial: string;
    nom: string;
    prenom: string;
    sexe: string;
    age: number;
    typePermis: PermisType;
}
