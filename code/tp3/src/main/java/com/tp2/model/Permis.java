package com.tp2.model;

import com.tp2.service.exception.HashingErrorException;
import com.tp2.utils.HashUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;

@Data
@NoArgsConstructor
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

    public boolean canBeHashed() {
        return this.id != null && this.citoyen.getId() != null;
    }

    private String createHash() throws IOException, HashingErrorException {
        if (canBeHashed()) {
            return HashUtil.Hash(id.toString(), citoyen.getId().toString());
        }
        throw new HashingErrorException();
    }

    public void generateHash() throws IOException, HashingErrorException {
        this.permisHash = createHash();
    }

    public String getHash() throws IOException, HashingErrorException {
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
