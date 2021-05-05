package com.tp2.repository;

import com.tp2.model.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CitoyenRepo extends JpaRepository<Citoyen, Integer> {

    Optional<Citoyen> findCitoyenByLoginIgnoreCaseAndPassword(@NotNull String login, @NotNull String password);

    Optional<Citoyen> findCitoyenByLoginIgnoreCase(@NotNull String login);

    Optional<Citoyen> findCitoyenByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);

    int deleteByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);
}