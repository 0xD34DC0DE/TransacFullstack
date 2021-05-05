package com.tp2.controller;

import com.google.zxing.WriterException;
import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import com.tp2.model.web.response.PermisData;
import com.tp2.service.CitoyenService;
import com.tp2.service.PermisService;
import com.tp2.service.QRService;
import com.tp2.service.exception.HashingErrorException;
import com.tp2.service.exception.InvalidPermisRequestException;
import com.tp2.service.exception.NonexistentUserException;
import com.tp2.service.exception.NullUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;

public class ControllerHelper {

    private final String QR_URL = "http://localhost:4200/permis/verify/";

    @Autowired
    CitoyenService citoyenService;

    @Autowired
    PermisService permisService;

    @Autowired
    QRService qrService;

    protected PermisData getPermisData(Permis permis, Citoyen citoyen) {
        Date dateExpiration = permisService.getExpirationDate(permis);
        String codeQRBase64;

        try {
            codeQRBase64 = qrService.base64QRImage(QR_URL + permis.getPermisHash());
        } catch (IOException | WriterException e) {
            return null;
        }

        return new PermisData(permis, citoyen, dateExpiration, codeQRBase64);
    }

    protected ResponseEntity<PermisData> createPermis(String nas, Permis.PermisType permisType) {

        Citoyen citoyen = citoyenService.getCitoyenByNumeroAssuranceSocial(nas);
        if(citoyen == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Citizen account not found"), HttpStatus.OK);

        PermisData permisData;

        try {
            Permis createdPermis;

            if (permisType == Permis.PermisType.TEST)
                createdPermis = permisService.createPermisTest(citoyen);
            else
                createdPermis = permisService.createPermisVaccin(citoyen);

            permisData = getPermisData(createdPermis, citoyen);

        } catch (IOException | HashingErrorException | NullUserException e) {
            return new ResponseEntity<>(PermisData.asErrorMessage("Error while creating permit"), HttpStatus.OK);
        } catch (NonexistentUserException e) {
            return new ResponseEntity<>(PermisData.asErrorMessage("User not found while creating permit"), HttpStatus.OK);
        } catch (InvalidPermisRequestException e) {
            return new ResponseEntity<>(PermisData.asErrorMessage("Permit was not authorized"), HttpStatus.OK);
        }

        if(permisData == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Error creating permit data"), HttpStatus.OK);
        else
            return new ResponseEntity<>(permisData, HttpStatus.OK);
    }

}
