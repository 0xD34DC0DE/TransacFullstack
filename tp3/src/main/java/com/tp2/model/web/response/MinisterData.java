package com.tp2.model.web.response;

import com.tp2.model.Permis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MinisterData extends ErrorMessage {

    private String numeroAssuranceSocial;

    private String nom;

    private String prenom;

    private String sexe;

    private Integer age;

    private Permis.PermisType typePermis;

    public static MinisterData asErrorMessage(String message) {
        MinisterData error = new MinisterData();
        error.setErrorMessage(message);
        return error;
    }

}
