package com.tp2.controller;

import com.tp2.model.Citoyen;
import com.tp2.model.Enfant;
import com.tp2.model.Permis;
import com.tp2.model.web.query.Credentials;
import com.tp2.model.web.query.EnfantRegisterData;
import com.tp2.model.web.query.CitoyenRegisteringData;
import com.tp2.model.web.response.*;

import com.tp2.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/user")
public class UserController extends ControllerHelper {

    @GetMapping("/minister/citoyen/{nas}")
    @CrossOrigin(origins = "http://localhost:4200")
    private ResponseEntity<MinisterData> getCitoyenMinister(@PathVariable String nas) {
        MinisterData citoyenMinisterData = citoyenService.getCitoyenMinisterData(nas);
        if(citoyenMinisterData == null)
            return new ResponseEntity<>(MinisterData.asErrorMessage("Invalid NAS"), HttpStatus.OK);
        else
            return new ResponseEntity<>(citoyenMinisterData, HttpStatus.OK);
    }

    @PostMapping(path = "/register/citoyen", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    private ResponseEntity registerCitoyen(@RequestBody CitoyenRegisteringData citoyenRegisteringData) {

        MinisterData ministerData = citoyenService.getCitoyenMinisterData(citoyenRegisteringData.getNumeroAssuranceSocial());
        if(ministerData == null)
            return new ResponseEntity<>(UserData.asErrorMessage("Error while trying to retrieve data from minister"), HttpStatus.OK);

        Citoyen newCitoyen = new Citoyen(citoyenRegisteringData.getNumeroAssuranceSocial(),
                citoyenRegisteringData.getLogin(),
                citoyenRegisteringData.getPassword(),
                ministerData.getNom(),
                ministerData.getPrenom(),
                ministerData.getSexe(),
                ministerData.getAge(),
                citoyenRegisteringData.getCourriel(),
                citoyenRegisteringData.getNumeroTelephone(),
                citoyenRegisteringData.getVilleResidence());

        if(newCitoyen.getCategoryAge() == Citoyen.CategoryAge.ENFANT)
            return new ResponseEntity<>(UserData.asErrorMessage("A parent account is needed to create this account"), HttpStatus.OK);

        Citoyen createdCitoyen;
        ResponseEntity<PermisData> permisDataResponseEntity = null;

        try {

            createdCitoyen = citoyenService.register(newCitoyen);

        } catch (NullUserException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Error while registering"), HttpStatus.OK);
        } catch (DuplicateNASException | DuplicateLoginException | MinisterInvalidNASException e) {
            return new ResponseEntity<>(UserData.asErrorMessage(e.getMessage()), HttpStatus.OK);
        }

        Permis createdPermis = null;

        permisDataResponseEntity = createPermis(createdCitoyen.getNumeroAssuranceSocial(), ministerData.getTypePermis());

        return processAccountCreationPermisErrors(ministerData, createdCitoyen, permisDataResponseEntity);
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    private ResponseEntity login(@RequestBody Credentials credentials) {
        try {
            UserData userData = new UserData(citoyenService.login(credentials.getLogin(), credentials.getPassword()));
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } catch (NullCredentialException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("No credentials provided"), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Wrong credentials"), HttpStatus.OK);
        }
    }

    @GetMapping("/enfant/getChildrenOf/{nas}")
    @CrossOrigin(origins = "http://localhost:4200")
    private ResponseEntity getEnfantsNASFromCitoyen(@PathVariable String nas) {

        List<Enfant> enfants;
        try {
            enfants = enfantService.getEnfantsFromCitoyen(nas);
        } catch (NonexistentUserException e) {
            return new ResponseEntity<>(NASList.asErrorMessage("User not found"), HttpStatus.OK);
        } catch (NullUserException e) {
            return new ResponseEntity<>(NASList.asErrorMessage("Server error"), HttpStatus.OK);
        }

        NASList nasList = new NASList(enfants.stream().map(Citoyen::getNumeroAssuranceSocial).collect(Collectors.toList()));
        return new ResponseEntity<>(nasList, HttpStatus.OK);
    }

    @PostMapping(path = "/register/enfant", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    private ResponseEntity registerEnfant(@RequestBody EnfantRegisterData enfantRegisterData) {
        Citoyen parent = citoyenService.getCitoyenByNumeroAssuranceSocial(enfantRegisterData.getParentNas());
        if(parent == null)
            return new ResponseEntity<>(UserData.asErrorMessage("Parent not found"), HttpStatus.OK);

        Citoyen enfant = citoyenService.getCitoyenByNumeroAssuranceSocial(enfantRegisterData.getEnfantNas());
        if(enfant != null)
            return new ResponseEntity<>(UserData.asErrorMessage("Account already exists"), HttpStatus.OK);

        MinisterData enfantMinisterData = citoyenService.getCitoyenMinisterData(enfantRegisterData.getEnfantNas());
        if(enfantMinisterData == null)
            return new ResponseEntity<>(UserData.asErrorMessage("Child not found in minister"), HttpStatus.OK);

        if(enfantMinisterData.getTypePermis() == null)
            return new ResponseEntity<>(UserData.asErrorMessage("No permit authorization found for the specified NAS"), HttpStatus.OK);

        ResponseEntity<PermisData> permisDataResponseEntity;
        Enfant createdEnfant;
        try {
             createdEnfant = new Enfant(
                    enfantMinisterData.getNumeroAssuranceSocial(),
                    enfantMinisterData.getNom(),
                    enfantMinisterData.getPrenom(),
                    enfantMinisterData.getSexe(),
                    enfantMinisterData.getAge(),
                    parent.getCourriel(),
                    parent.getNumeroTelephone(),
                    parent.getVilleResidence(),
                    parent);

            createdEnfant = enfantService.register(createdEnfant);

            permisDataResponseEntity = createPermis(createdEnfant.getNumeroAssuranceSocial(), enfantMinisterData.getTypePermis());

        } catch (IOException | NullUserException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Error creating child"), HttpStatus.OK);
        } catch (DuplicateNASException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Child already exists"), HttpStatus.OK);
        } catch (NonexistentUserException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Parent does not exists"), HttpStatus.OK);
        } catch (MinisterInvalidNASException e) {
            return new ResponseEntity<>(UserData.asErrorMessage("Child nas is invalid according to minister"), HttpStatus.OK);
        }

        return processAccountCreationPermisErrors(enfantMinisterData, createdEnfant, permisDataResponseEntity);
    }

    private ResponseEntity processAccountCreationPermisErrors(MinisterData ministerData, Citoyen createdCitoyen, ResponseEntity<PermisData> permisDataResponseEntity) {

        if(permisDataResponseEntity.getBody() != null && permisDataResponseEntity.getBody().isError()) {
            cancelAccountCreation(ministerData.getNumeroAssuranceSocial());
            return new ResponseEntity<>(UserData.asErrorMessage(permisDataResponseEntity.getBody().getErrorMessage()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new UserData(createdCitoyen), HttpStatus.OK);
    }

    private boolean cancelAccountCreation(String nas) {
        return citoyenService.deleteCitoyenByNumeroAssuranceSocial(nas) > 0;
    }

}
