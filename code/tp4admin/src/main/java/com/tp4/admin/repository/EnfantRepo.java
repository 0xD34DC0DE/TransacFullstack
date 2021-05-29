package com.tp4.admin.repository;

import com.sun.istack.NotNull;
import com.tp4.admin.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnfantRepo extends JpaRepository<Enfant, Integer> {

    List<Enfant> findEnfantsByParentId(@NotNull Integer parentId);

    Optional<Enfant> findEnfantByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);

}