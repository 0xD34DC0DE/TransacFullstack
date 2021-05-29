package com.tp4.admin.model;

import com.tp4.admin.utils.HashUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PERMIS_VACCIN")
public class Permis implements Serializable {

    public final static int validDurationDays = 99999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String permisHash;

    @NotNull
    private Citoyen.CategoryAge categorieAge;

    @NotNull
    @ManyToOne
    private Citoyen citoyen;

    @NotNull
    private PermisType typePermis;

    public Permis(@NotNull Citoyen citoyen) {
        this.categorieAge = citoyen.getCategoryAge();
        this.citoyen = citoyen;
        this.typePermis = PermisType.VACCIN;
    }

    public Permis() {
        this.typePermis = PermisType.VACCIN;
    }

    @PrePersist
    public void setAutomaticFields() {
        this.categorieAge = citoyen.getCategoryAge();
    }

    public boolean canBeHashed() {
        return this.id != null && this.citoyen.getId() != null;
    }

    private String createHash() throws Exception {
        if (canBeHashed()) {
            return HashUtils.Hash(id.toString(), citoyen.getId().toString());
        }
        throw new Exception("Could not hash permit");
    }

    public void generateHash() throws Exception {
        this.permisHash = createHash();
    }

    public String getHash() throws Exception {
        return createHash();
    }

    public boolean isValid() {
        return true;
    }

    public enum PermisType {
        VACCIN,
        TEST
    }
}
