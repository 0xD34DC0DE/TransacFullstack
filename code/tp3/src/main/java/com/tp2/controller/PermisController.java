package com.tp2.controller;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import com.tp2.model.PermisTest;
import com.tp2.model.web.response.PermisData;
import com.tp2.model.web.response.PermisValidityData;
import com.tp2.model.web.response.UserData;
import com.tp2.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/permis")
public class PermisController extends ControllerHelper {

    @PostMapping("/renew/{nas}")
    @CrossOrigin
    private ResponseEntity renewPermis(@PathVariable String nas, @RequestParam(required = false) Permis.PermisType type) {

        if(type == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Missing permit type"), HttpStatus.BAD_REQUEST);

        Citoyen citoyen = citoyenService.getCitoyenByNumeroAssuranceSocial(nas);
        if(citoyen == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Citoyen not found"), HttpStatus.OK);

        PermisData permisData;

        try {
            if (type == Permis.PermisType.TEST) {
                PermisTest permisTest = permisService.createPermisTest(citoyen);

                if (permisTest == null)
                    return new ResponseEntity<>(PermisData.asErrorMessage("Permit renew failed"), HttpStatus.OK);

                permisData = getPermisData(permisTest, citoyen);
            } else {
                return new ResponseEntity<>(PermisData.asErrorMessage("Bad permit type for renewal"), HttpStatus.BAD_REQUEST);
            }
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

    @GetMapping("/get/{nas}")
    @CrossOrigin
    private ResponseEntity getPermisByNumeroAssuranceSocial(@PathVariable String nas) {

        Citoyen citoyen = citoyenService.getCitoyenByNumeroAssuranceSocial(nas);
        if(citoyen == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("User not found"), HttpStatus.OK);

        Permis permis = permisService.getPermisByCitoyenId(citoyen.getId());
        if(permis == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Permit not found"), HttpStatus.OK);

        PermisData permisData = getPermisData(permis, citoyen);
        if(permisData == null)
            return new ResponseEntity<>(PermisData.asErrorMessage("Error creating permit data"), HttpStatus.OK);

        return new ResponseEntity<>(permisData, HttpStatus.OK);
    }

    @GetMapping("/verify/{hash}")
    @CrossOrigin
    private ResponseEntity isPermitValidByHash(@PathVariable String hash) {
        try {
            return new ResponseEntity<>(new PermisValidityData(permisService.isPermisValidByHash(hash)), HttpStatus.OK);
        } catch (NullHashException e) {
            return new ResponseEntity<>(PermisValidityData.asErrorMessage("Permit has is null"), HttpStatus.OK);
        }
    }

}
