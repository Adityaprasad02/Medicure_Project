package com.patient_management.project_03.Security.model;


import com.patient_management.project_03.model.TestDepartments;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Admins {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true , nullable = false)
    private String username;

 
    @Column(nullable = false)
    private String password;
    

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role ; 

    // Additional field for Department ID if needed
    @ManyToOne
    @JoinColumn(name = "departmentId" , nullable = true)
    private TestDepartments testDepartment ;
}
