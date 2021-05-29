package com.tp4.admin.repository;

import com.tp4.admin.model.Permis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisRepo extends JpaRepository<Permis, Integer> {
  List<Permis> findAllByCitoyen_Id(Integer citoyenId);
}
