package com.patient_management.project_03.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    private String id ; 

    
    @NotNull
    private String name ; 
    
    @NotNull
    @Email
    @Column(unique = true)
    private String email ; 

    @NotNull
    private LocalDate dateOfBirth ; 
    
    @NotNull
    private LocalDate registeredDate ; 

    @NotNull
    private String address ; 
    
}
