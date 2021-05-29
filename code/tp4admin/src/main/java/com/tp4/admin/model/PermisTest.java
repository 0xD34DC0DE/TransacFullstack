package com.tp4.admin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PERMIS_TEST")
public class PermisTest extends Permis {

    static public final int VALID_DURATION_DAYS = 14;
    static public final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Transient
    private String dateCreationHTML; // Date used for html input since the format is locked to yyyy-MM-dd

    public PermisTest(@NotNull Citoyen citoyen) {
        super(citoyen);
        this.dateCreation = new Date();
        this.setTypePermis(PermisType.TEST);
    }

    public PermisTest(@NotNull Citoyen citoyen, Date dateCreation) {
        super(citoyen);
        this.dateCreation = dateCreation;
        super.setTypePermis(PermisType.TEST);
    }

    public PermisTest() {
        this.setTypePermis(PermisType.TEST);
        this.dateCreation = new Date();
        generateHTMLDate(null);
    }

    @Override
    public boolean isValid() {
        Date expirationDate = new Date(this.dateCreation.getTime() + Duration.ofDays(VALID_DURATION_DAYS).toMillis());
        return new Date().compareTo(expirationDate) <= 0;
    }

    public void generateHTMLDate(String pattern) {
        if(pattern == null)
            pattern = DEFAULT_DATE_PATTERN;

        DateFormat targetFormat = new SimpleDateFormat(pattern);
        dateCreationHTML = targetFormat.format(dateCreation);
    }
}

