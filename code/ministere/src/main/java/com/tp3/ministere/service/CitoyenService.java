package com.tp3.ministere.service;

import com.tp3.ministere.model.Citoyen;
import com.tp3.ministere.repository.CitoyenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitoyenService {

    @Autowired
    CitoyenRepo citoyenRepo;

    public Optional<Citoyen> findCitoyenByNumeroAssuranceSocial(String numeroAssuranceSocial) {
        return citoyenRepo.findCitoyenByNumeroAssuranceSocial(numeroAssuranceSocial);
    }

}
