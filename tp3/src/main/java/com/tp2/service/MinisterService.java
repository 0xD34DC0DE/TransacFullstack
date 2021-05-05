package com.tp2.service;

import com.tp2.model.Permis;
import com.tp2.model.responseEntity.CitoyenMinisterData;

import com.tp2.model.web.response.MinisterData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MinisterService {

    private static final String MINISTER_URL = "http://localhost:9191/ministere/";

    public boolean verifyNumeroAssuranceSocial(String numeroAssuranceSocial) {
        MinisterData response = ministerCitoyenQuery(numeroAssuranceSocial);
        return response != null;
    }

    public boolean isPermisRequestValid(String numeroAssuranceSocial) {
        Permis.PermisType typePermis = getPermisTypeForNumeroAssuranceSocial(numeroAssuranceSocial);
        if (typePermis == null)
            return false;
        else
            return typePermis.equals(Permis.PermisType.VACCIN);
    }

    public boolean isTestRequestValid(String numeroAssuranceSocial) {
        Permis.PermisType typePermis = getPermisTypeForNumeroAssuranceSocial(numeroAssuranceSocial);
        if (typePermis == null)
            return false;
        else
            return typePermis.equals(Permis.PermisType.TEST);
    }

    private Permis.PermisType getPermisTypeForNumeroAssuranceSocial(String numeroAssuranceSocial) {
        MinisterData citoyenMinisterData = ministerCitoyenQuery(numeroAssuranceSocial);
        if (citoyenMinisterData == null)
            return null;
        else
            return citoyenMinisterData.getTypePermis();
    }

    public MinisterData ministerCitoyenQuery(String numeroAssuranceSocial) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(MINISTER_URL + numeroAssuranceSocial, MinisterData.class);
    }

}