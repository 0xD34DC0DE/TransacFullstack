package com.tp4.admin.repository;

import com.sun.istack.NotNull;
import com.tp4.admin.model.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitoyenRepo extends JpaRepository<Citoyen, Integer> {

    Optional<Citoyen> findByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);
}
