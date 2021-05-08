package com.tp2.repository;

import com.tp2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    List<Admin> findAdminByLoginAndPassword(@NotNull String login, @NotNull String password);
}
