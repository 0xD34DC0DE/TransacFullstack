package com.tp2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("PERMIS_TEST")
public class PermisTest extends Permis {

    static public final int validDurationDays = 14;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    public PermisTest(@NotNull Citoyen citoyen) {
        super(citoyen);
        this.dateCreation = new Date();
        this.setTypePermis(PermisType.TEST);
    }

    public PermisTest(@NotNull Citoyen citoyen, Date dateCreation) {
        super(citoyen);
        this.dateCreation = dateCreation;
        this.setTypePermis(PermisType.TEST);
    }

    @Override
    public boolean isValid() {
        Date expirationDate = new Date(this.dateCreation.getTime() + Duration.ofDays(validDurationDays).toMillis());
        return new Date().compareTo(expirationDate) <= 0;
    }
}
