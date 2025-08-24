package com.patient_management.project_03.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testResultId;

    private String patientId;


    // each visit case can have multiple test results
    @ManyToOne
    @JoinColumn(name = "caseId", nullable = false)
    private VisitCase visitCase;


    private Long testDepartmentId;
    private String departmentName;
    private String headOfDepartment;
    private String resultMessage;

}
