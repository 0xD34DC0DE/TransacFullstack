package com.tp2.model.web.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NASList extends ErrorMessage {

    List<String> nasList;

    public static NASList asErrorMessage(String message) {
        NASList error = new NASList();
        error.setErrorMessage(message);
        return error;
    }
}
