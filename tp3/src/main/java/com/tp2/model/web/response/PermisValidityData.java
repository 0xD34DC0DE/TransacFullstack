package com.tp2.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PermisValidityData extends ErrorMessage {

    private boolean valid;

    public static PermisValidityData asErrorMessage(String message) {
        PermisValidityData error = new PermisValidityData();
        error.setErrorMessage(message);
        return error;
    }
}
