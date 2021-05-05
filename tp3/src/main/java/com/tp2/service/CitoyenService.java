package com.tp2.service;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import com.tp2.model.web.response.MinisterData;
import com.tp2.repository.CitoyenRepo;

import com.tp2.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class CitoyenService {

    @Autowired
    CitoyenRepo citoyenRepo;

    @Autowired
    MinisterService ministerService;

    public Citoyen register(Citoyen citoyen) throws NullUserException, DuplicateNASException, DuplicateLoginException, MinisterInvalidNASException {

        if (citoyen == null) {
            throw new NullUserException();
        }

        if (citoyenRepo.findCitoyenByNumeroAssuranceSocial(citoyen.getNumeroAssuranceSocial()).isPresent()) {
            throw new DuplicateNASException();
        }

        if (citoyenRepo.findCitoyenByLoginIgnoreCase(citoyen.getLogin()).isPresent()) {
            throw new DuplicateLoginException();
        }

        if (!ministerService.verifyNumeroAssuranceSocial(citoyen.getNumeroAssuranceSocial())) {
            throw new MinisterInvalidNASException();
        }

        return citoyenRepo.save(citoyen);
    }

    public Citoyen login(@NotNull String login, @NotNull String password) throws NullCredentialException, BadCredentialsException {

        if (login == null || password == null) {
            throw new NullCredentialException();
        }

        return citoyenRepo.findCitoyenByLoginIgnoreCaseAndPassword(login, password).orElseThrow(BadCredentialsException::new);
    }

    public MinisterData getCitoyenMinisterData(String numeroAssuranceSocial) {
        return ministerService.ministerCitoyenQuery(numeroAssuranceSocial);
    }

    public boolean isPermisRequestValid(String numeroAssuranceSocial, Permis.PermisType permisType) {
        switch (permisType) {
            case VACCIN:
                return ministerService.isPermisRequestValid(numeroAssuranceSocial);
            case TEST:
                return ministerService.isTestRequestValid(numeroAssuranceSocial);
        }

        return false;
    }

    public Citoyen getCitoyenByNumeroAssuranceSocial(String numeroAssuranceSocial) {
        return citoyenRepo.findCitoyenByNumeroAssuranceSocial(numeroAssuranceSocial).orElse(null);
    }

    public int deleteCitoyenByNumeroAssuranceSocial(String numeroAssuranceSocial) {
        return citoyenRepo.deleteByNumeroAssuranceSocial(numeroAssuranceSocial);
    }





}
