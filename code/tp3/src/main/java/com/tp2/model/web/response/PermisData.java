package com.tp2.model.web.response;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PermisData extends ErrorMessage {
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

    public static PermisData asErrorMessage(String message) {
        PermisData error = new PermisData();
        error.setErrorMessage(message);
        return error;
    }
}
