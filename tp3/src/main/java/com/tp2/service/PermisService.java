package com.tp2.service;

import com.tp2.model.Citoyen;
import com.tp2.model.Permis;
import com.tp2.model.PermisTest;
import com.tp2.repository.CitoyenRepo;
import com.tp2.repository.PermisRepo;
import com.tp2.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PermisService {

    private final Duration permisTestValidDuration = Duration.ofDays(PermisTest.validDurationDays);
    private final Duration permisVaccinValidDuration = Duration.ofDays(Permis.validDurationDays);

    @Autowired
    private PermisRepo permisRepo;

    @Autowired
    private CitoyenRepo citoyenRepo;

    @Autowired
    private MinisterService ministerService;

    public Permis createPermisVaccin(@NotNull Citoyen citoyen) throws NullUserException, NonexistentUserException, InvalidPermisRequestException, IOException, HashingErrorException {

        if (citoyen == null) {
            throw new NullUserException();
        }

        if(!isCitoyenValid(citoyen)) {
            throw new NonexistentUserException();
        }

        if(!ministerService.isPermisRequestValid(citoyen.getNumeroAssuranceSocial())) {
            throw new InvalidPermisRequestException("Permis vaccin request invalid");
        }

        Permis permis = permisRepo.save(new Permis(citoyen));
        permis.generateHash();
        return permisRepo.save(permis);
    }

    public PermisTest createPermisTest(@NotNull Citoyen citoyen) throws NullUserException, NonexistentUserException, InvalidPermisRequestException, IOException, HashingErrorException {

        if (citoyen == null) {
            throw new NullUserException();
        }

        if(!isCitoyenValid(citoyen)) {
            throw new NonexistentUserException();
        }

        if(!ministerService.isTestRequestValid(citoyen.getNumeroAssuranceSocial())) {
            throw new InvalidPermisRequestException("Permis test request invalid");
        }

        PermisTest permis = permisRepo.save(new PermisTest(citoyen));
        permis.generateHash();
        return permisRepo.save(permis);
    }

    public boolean isPermisValidByHash(@NotNull String hash) throws NullHashException {

        if (hash == null) {
            throw new NullHashException();
        }

        Optional<Permis> permis = permisRepo.findPermisByPermisHash(hash);

        return permis.map(Permis::isValid).orElse(false);
    }

    private boolean isCitoyenValid(@NotNull Citoyen citoyen) throws NullUserException, NonexistentUserException {

        if (citoyen == null) {
            throw new NullUserException();
        }

        if (citoyenRepo.findCitoyenByNumeroAssuranceSocial(citoyen.getNumeroAssuranceSocial()).isEmpty()) {
            throw new NonexistentUserException();
        }

        return true;
    }

    public Permis getPermisByCitoyenId(Integer citoyenId) {
        List<Permis> permis = permisRepo.findPermisByCitoyen_Id(citoyenId);

        if(permis.size() == 0) {
            return null;
        }

        if(permis.size() > 1) {
            if(permis.get(0) instanceof PermisTest) {
                permis.sort(Comparator.comparing(p -> ((PermisTest)p).getDateCreation()).reversed());
                return permis.get(0);
            }
        }

        return permis.get(0);
    }

    public Date getExpirationDate(Permis permis) {
        if(permis instanceof PermisTest) {
            return new Date(((PermisTest)permis).getDateCreation().getTime() + permisTestValidDuration.toMillis());
        } else {
            return new Date(new Date().getTime() + permisVaccinValidDuration.toMillis());
        }
    }



}
