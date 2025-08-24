package com.patient_management.project_03.exception;

public class EmailAlreadyExistsException extends RuntimeException {
   public EmailAlreadyExistsException(String Message){
      super(Message) ; 
   }
}
