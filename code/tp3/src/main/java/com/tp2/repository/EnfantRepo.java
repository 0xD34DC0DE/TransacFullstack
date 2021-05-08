package com.tp2.repository;

import com.tp2.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface EnfantRepo extends JpaRepository<Enfant, Integer> {

    List<Enfant> findEnfantsByParentId(@NotNull Integer parentId);

    Optional<Enfant> findEnfantByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);

    int deleteEnfantByNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);

}
