package com.tp2.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailSendResult extends ErrorMessage {
    boolean successful;

    public static EmailSendResult asErrorMessage(String message) {
        EmailSendResult error = new EmailSendResult();
        error.setErrorMessage(message);
        return error;
    }
}
