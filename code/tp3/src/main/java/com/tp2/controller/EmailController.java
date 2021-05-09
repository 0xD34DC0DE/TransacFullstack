package com.tp2.controller;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import com.tp2.model.web.response.EmailSendResult;
import com.tp2.service.CitoyenService;
import com.tp2.service.EmailService;
import com.tp2.service.PermisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    CitoyenService citoyenService;

    @Autowired
    PermisService permisService;

    @GetMapping("/send/image/{nas}")
    @CrossOrigin
    private ResponseEntity<EmailSendResult> sendImageMail(@PathVariable String nas) {

        Citoyen citoyen = citoyenService.getCitoyenByNumeroAssuranceSocial(nas);
        if(citoyen == null)
            return new ResponseEntity<>(EmailSendResult.asErrorMessage("User not found"), HttpStatus.OK);

        Permis permis = permisService.getPermisByCitoyenId(citoyen.getId());
        if(permis == null)
            return new ResponseEntity<>(EmailSendResult.asErrorMessage("Permit not found"), HttpStatus.OK);

        try {
            emailService.SendQRImageMail(permis, citoyen.getCourriel());
        } catch (Exception e) {
            return new ResponseEntity<>(new EmailSendResult(false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new EmailSendResult(true), HttpStatus.OK);
    }

    @GetMapping("/send/pdf/{nas}")
    @CrossOrigin
    private ResponseEntity<EmailSendResult> sendPdfMail(@PathVariable String nas) {

        Citoyen citoyen = citoyenService.getCitoyenByNumeroAssuranceSocial(nas);
        if(citoyen == null)
            return new ResponseEntity<>(EmailSendResult.asErrorMessage("User not found"), HttpStatus.OK);

        Permis permis = permisService.getPermisByCitoyenId(citoyen.getId());
        if(permis == null)
            return new ResponseEntity<>(EmailSendResult.asErrorMessage("Permit not found"), HttpStatus.OK);

        try {
            emailService.SendPDFQRImageMail(permis, citoyen.getCourriel());
        } catch (Exception e) {
            return new ResponseEntity<>(new EmailSendResult(false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new EmailSendResult(true), HttpStatus.OK);
    }

}
