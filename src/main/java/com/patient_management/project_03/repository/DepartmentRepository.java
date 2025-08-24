package com.patient_management.project_03.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient_management.project_03.model.TestDepartments;

public interface DepartmentRepository extends JpaRepository<TestDepartments, Long> {

}
