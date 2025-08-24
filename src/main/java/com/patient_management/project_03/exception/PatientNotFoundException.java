package com.patient_management.project_03.exception;

public class PatientNotFoundException extends RuntimeException {
     
    public PatientNotFoundException(String message){
        super(message) ; 
    }

}
