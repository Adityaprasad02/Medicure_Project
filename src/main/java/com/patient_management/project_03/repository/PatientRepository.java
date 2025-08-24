package com.patient_management.project_03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patient_management.project_03.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient , String> {
   Optional<Patient> findTopByOrderByIdDesc() ; 
   boolean existsByEmail(String Email) ; 
   boolean existsByEmailAndIdNot(String Email , String id) ;

}
