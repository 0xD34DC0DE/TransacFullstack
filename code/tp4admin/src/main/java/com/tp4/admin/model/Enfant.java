package com.tp4.admin.model;

import com.sun.istack.NotNull;
import com.tp4.admin.utils.HashUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.IOException;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@Entity
public class Enfant extends Citoyen implements Serializable {

    @NotNull
    @ManyToOne // If the parent gets deleted, don't deleted the child
    private Citoyen parent;

    public Enfant(@NotNull String numeroAssuranceSocial,
                  @NotNull String nom,
                  @NotNull String prenom,
                  @NotNull String sexe,
                  @NotNull Integer age,
                  @NotNull String courriel,
                  @NotNull String numeroTelephone,
                  @NotNull String villeResidence,
                  @NotNull Citoyen parent) throws IOException {
        super(numeroAssuranceSocial, HashUtils.Hash(numeroAssuranceSocial), "", nom, prenom, sexe, age, courriel, numeroTelephone, villeResidence);
        this.parent = parent;
        this.setCitoyenType(CitoyenType.ENFANT);
    }


}
