package com.patient_management.project_03.dto;

import com.patient_management.project_03.dto.validators.createPatientValidationGroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// use Lombok for getters and setters
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {
    @NotBlank(message = "Name is Required")
    @Size(max = 100 , message = "Name can't exceed 100 characters")
    private String name ; 

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email ; 

    @NotBlank(message = "address is required")
    private String address ;
        
    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth ; 

    
    @NotBlank(groups = createPatientValidationGroups.class , message = "Registration Date is required")
    private String registeredDate ;
    
}
