package com.patient_management.project_03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    private Long resultId;
    private String patientId ;
    private Long caseId ;
    private Long TestdepartmentId ; 
    private String departmentName;
    private String HeadOfDepartment;
    private String resultMessage ;
}
