package com.patient_management.project_03.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(
        MethodArgumentNotValidException ex){
            
            Map<String,String> errors = new HashMap<>() ; 

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

            return ResponseEntity.badRequest().body(errors) ; 
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
         public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException
         ex){
            Map<String,String> errors = new HashMap<>() ; 

            errors.put("message" , "Email Account Already Do Exists") ; 

            return ResponseEntity.badRequest().body(errors) ; 
            
         }
     
   @ExceptionHandler(PatientNotFoundException.class)
      public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException
      ex){
        Map<String,String> errors = new HashMap<>() ; 
        
        errors.put("message" , "Patient with given ID doesn't exist") ;
        
        return ResponseEntity.badRequest().body(errors) ; 

      }
      
      @ExceptionHandler(RuntimeException.class)
      public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex){
        Map<String,String> errors = new HashMap<>() ; 
        
        errors.put("message" , ex.getMessage()) ; 
        
        return ResponseEntity.badRequest().body(errors) ; 
      }
}
