package com.tp3.ministere.repository;

import com.tp3.ministere.model.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitoyenRepo extends JpaRepository<Citoyen, Integer> {

    Optional<Citoyen> findCitoyenByNumeroAssuranceSocial(String numeroAssuranceSocial);

}