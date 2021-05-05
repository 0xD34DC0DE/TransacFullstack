package com.tp2.model.responseEntity;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import lombok.Data;

import java.util.Date;

@Data
public class PermisData {

    private Citoyen.CategoryAge categorieAge;

    private Permis.PermisType typePermis;

    private String nom;

    private String prenom;

    private Date dateExpiration;

    private String codeQRBase64;

    public PermisData(Permis permis, Citoyen citoyen, Date dateExpiration, String codeQRBase64) {
        this.categorieAge = citoyen.getCategoryAge();
        this.typePermis = permis.getTypePermis();
        this.nom = citoyen.getNom();
        this.prenom = citoyen.getPrenom();
        this.dateExpiration = dateExpiration;
        this.codeQRBase64 = codeQRBase64;
    }

}
