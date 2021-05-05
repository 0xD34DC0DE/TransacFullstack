package com.tp2.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NAS extends ErrorMessage {
    private String nas;

    public static NAS asErrorMessage(String message) {
        NAS error = new NAS();
        error.setErrorMessage(message);
        return error;
    }
}
