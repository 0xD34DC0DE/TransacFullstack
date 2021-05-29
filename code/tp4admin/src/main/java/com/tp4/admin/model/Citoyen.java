package com.tp4.admin.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Citoyen extends User {

    @NotNull
    @Column(unique = true)
    private String numeroAssuranceSocial;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String sexe;

    @NotNull
    private Integer age;

    @NotNull
    private String courriel;

    @NotNull
    private String numeroTelephone;

    @NotNull
    private String villeResidence;

    private CategoryAge categoryAge;

    private CitoyenType citoyenType;

    public Citoyen(@NotNull String numeroAssuranceSocial,
                   @NotNull String login,
                   @NotNull String password,
                   @NotNull String nom,
                   @NotNull String prenom,
                   @NotNull String sexe,
                   @NotNull Integer age,
                   @NotNull String courriel,
                   @NotNull String numeroTelephone,
                   @NotNull String villeResidence) {
        super(login, password);
        this.numeroAssuranceSocial = numeroAssuranceSocial;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.age = age;
        this.courriel = courriel;
        this.numeroTelephone = numeroTelephone;
        this.villeResidence = villeResidence;
        setAutomaticFields();
    }

    @PrePersist
    private void setAutomaticFields() {
        this.categoryAge = CategoryAge.construct(age);
        assert (this.categoryAge != CategoryAge.INVALID);
        this.citoyenType = CitoyenType.CITOYEN;
    }

    public enum CategoryAge {
        ENFANT("enfant"),
        JEUNE("jeune"),
        ADULTE("adulte"),
        SENIOR("senior"),
        INVALID("invalid");

        private final String category;

        public String getCategory() {
            return category;
        }

        CategoryAge(final String category) {
            this.category = category;
        }

        public static CategoryAge construct(int age) {
            if (age >= 0 && age <= 16) return ENFANT;
            if (age >= 17 && age <= 22) return JEUNE;
            if (age >= 23 && age <= 65) return ADULTE;
            if (age >= 65) return SENIOR;
            return INVALID;
        }
    }

    public enum CitoyenType {
        CITOYEN,
        ENFANT
    }
}
