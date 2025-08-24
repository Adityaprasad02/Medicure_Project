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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;
    

    @ManyToOne
    @JoinColumn(name = "PatientId" , nullable = false)
    private Patient patient ;

    private String visitPurpose;

    private String visitDate;

    private String ValidTill ;
    
    private String doctorName;
    private String department;
    

}
