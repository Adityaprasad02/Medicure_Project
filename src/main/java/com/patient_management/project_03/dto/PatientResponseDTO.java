package com.patient_management.project_03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {

    private String id ; 
    private String name ; 
    private String email ; 
    private String address ; 
    private String dateOfBirth ;
    private String registeredDate ; 
    
}
