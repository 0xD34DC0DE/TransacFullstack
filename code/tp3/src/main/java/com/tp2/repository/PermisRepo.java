package com.tp2.repository;

import com.tp2.model.Permis;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PermisRepo extends JpaRepository<Permis, Integer> {

    List<Permis> findPermisByCitoyen_Id(@NotNull Integer citoyen_id);

    Optional<Permis> findPermisByPermisHash(@NotNull String permisHash);

    List<Permis> deleteAllByCitoyenNumeroAssuranceSocial(@NotNull String numeroAssuranceSocial);
}
