package com.tp2.model.web.response;

import com.tp2.model.Citoyen;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserData extends ErrorMessage {

    private String numeroAssuranceSocial;

    private String nom;

    private String prenom;

    private String sexe;

    private Integer age;

    private String courriel;

    private String numeroTelephone;

    private String villeResidence;

    public UserData(Citoyen citoyen) {
        this.numeroAssuranceSocial = citoyen.getNumeroAssuranceSocial();
        this.nom = citoyen.getNom();
        this.prenom = citoyen.getPrenom();
        this.sexe = citoyen.getSexe();
        this.age = citoyen.getAge();
        this.courriel = citoyen.getCourriel();
        this.numeroTelephone = citoyen.getNumeroTelephone();
        this.villeResidence = citoyen.getVilleResidence();
    }

    public static UserData asErrorMessage(String message) {
        UserData error = new UserData();
        error.setErrorMessage(message);
        return error;
    }
}
