package com.tp2.model.web.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitoyenRegisteringData {

    private String numeroAssuranceSocial;

    private String courriel;

    private String numeroTelephone;

    private String villeResidence;

    private String login;

    private String password;

}
