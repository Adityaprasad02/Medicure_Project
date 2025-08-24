package com.patient_management.project_03.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient_management.project_03.Security.model.Admins;

public interface adminRepo extends JpaRepository<Admins, Long> {

    boolean existsByUsername(String username);
        // Additional query methods can be defined here as needed

    Optional<Admins> findByUsername(String username);
}
