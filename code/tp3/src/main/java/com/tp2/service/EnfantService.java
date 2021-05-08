package com.tp2.service;

import com.tp2.model.Citoyen;
import com.tp2.model.Enfant;
import com.tp2.repository.CitoyenRepo;
import com.tp2.repository.EnfantRepo;
import com.tp2.service.exception.DuplicateNASException;
import com.tp2.service.exception.MinisterInvalidNASException;
import com.tp2.service.exception.NonexistentUserException;
import com.tp2.service.exception.NullUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class EnfantService {

    @Autowired
    EnfantRepo enfantRepo;

    @Autowired
    CitoyenRepo citoyenRepo;

    @Autowired
    MinisterService ministerService;

    public Enfant register(@NotNull Enfant enfant) throws NullUserException, DuplicateNASException, MinisterInvalidNASException, NonexistentUserException {

        if (enfant == null) {
            throw new NullUserException();
        }

        if (enfantRepo.findEnfantByNumeroAssuranceSocial(enfant.getNumeroAssuranceSocial()).isPresent()) {
            throw new DuplicateNASException();
        }

        if (!ministerService.verifyNumeroAssuranceSocial(enfant.getNumeroAssuranceSocial())) {
            throw new MinisterInvalidNASException();
        }

        if (citoyenRepo.findCitoyenByNumeroAssuranceSocial(enfant.getParent().getNumeroAssuranceSocial()).isEmpty()) {
            throw new NonexistentUserException();
        }

        return enfantRepo.save(enfant);
    }

    public List<Enfant> getEnfantsFromCitoyen(@NotNull String numeroAssuranceSocial) throws NullUserException, NonexistentUserException {

        if (numeroAssuranceSocial == null) {
            throw new NullUserException();
        }

        Optional<Citoyen> parent = citoyenRepo.findCitoyenByNumeroAssuranceSocial(numeroAssuranceSocial);
        if (parent.isEmpty()) {
            throw new NonexistentUserException();
        }

        if (parent.get().getId() == null) {
            throw new NullUserException();
        }

        return enfantRepo.findEnfantsByParentId(parent.get().getId());
    }

}
