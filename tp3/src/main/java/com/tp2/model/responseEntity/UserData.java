package com.tp2.model.responseEntity;

import com.tp2.model.Citoyen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

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
}
