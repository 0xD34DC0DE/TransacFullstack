package com.tp2.model;

import com.tp2.utils.HashUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Polymorphism(type = PolymorphismType.EXPLICIT)
// Prevents this class from being included when doing polymorphic queries on Citoyen's class
@Entity
public class Enfant extends Citoyen implements Serializable {

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST}) // If the parent gets deleted, don't deleted the child
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
        super(numeroAssuranceSocial, HashUtil.Hash(numeroAssuranceSocial), "", nom, prenom, sexe, age, courriel, numeroTelephone, villeResidence);
        this.parent = parent;
        this.setCitoyenType(CitoyenType.ENFANT);
    }

}
