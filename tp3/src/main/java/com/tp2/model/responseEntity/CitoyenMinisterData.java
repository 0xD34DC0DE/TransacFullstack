package com.tp2.model.responseEntity;

import com.tp2.model.Permis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CitoyenMinisterData extends ErrorData {

    @Id
    private String numeroAssuranceSocial;

    private String nom;

    private String prenom;

    private String sexe;

    private Integer age;

    private Permis.PermisType typePermis;

    public CitoyenMinisterData(String errorMessage) {
        setErrorMessage(errorMessage);
    }
}