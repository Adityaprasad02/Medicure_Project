package com.patient_management.project_03.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDepartments {
    @Id
    private Long departmentId ; 

    private String departmentName;

    private String HeadOfDepartment;


}
