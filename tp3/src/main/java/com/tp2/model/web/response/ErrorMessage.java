package com.tp2.model.web.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
    private String errorMessage;

    public boolean isError() {
        if(errorMessage == null)
            return false;
        return !errorMessage.isEmpty();
    }
}
