package com.patient_management.project_03.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient_management.project_03.model.VisitCase;

public interface VisitCaseRepository extends JpaRepository<VisitCase, Long> {
   
    List<VisitCase> findByPatientId(String patientId);

    boolean existsByPatientId(String patientId);
}
