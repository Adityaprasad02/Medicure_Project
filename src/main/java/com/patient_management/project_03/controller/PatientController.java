package com.patient_management.project_03.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient_management.project_03.dto.PatientRequestDTO;
import com.patient_management.project_03.dto.PatientResponseDTO;
import com.patient_management.project_03.dto.validators.createPatientValidationGroups;
import com.patient_management.project_03.model.VisitCase;
import com.patient_management.project_03.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;









@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://medicoregeneralhospital.netlify.app"})
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for Managing Patients")
public class PatientController {

     // will Inject PatientService 
     private final PatientService patientService ;

     public PatientController(PatientService patientService) {
        this.patientService = patientService;
     }
     
     @GetMapping // default path /patients
     @Operation(summary = "Get Patients")
     @PreAuthorize("hasAuthority('READ_ALL_PATIENT')")
     public ResponseEntity<List<PatientResponseDTO>> getPatients() {
      List<PatientResponseDTO> patients =  patientService.getPatients() ;
      return ResponseEntity.ok().body(patients) ; 
     }

     
     @GetMapping("/{id}")
     @PreAuthorize("hasAuthority('READ_PATIENT_BY_ID')")
     public ResponseEntity<PatientResponseDTO> getPatientByID(@PathVariable String id) {
         return ResponseEntity.ok(patientService.getPatientbyId(id));
     }
     
     @PostMapping
     @Operation(summary = "Create a Patient")
     @PreAuthorize("hasAuthority('CREATE_PATIENT')")
     public ResponseEntity<PatientResponseDTO> createPatient(
      @Validated({Default.class , createPatientValidationGroups.class})
      @RequestBody PatientRequestDTO patientRequestDTO) {

         //createPatientValidationGroups.class -> validation for registeredDate along with PatientRequest
         // we have used groups in PatientRequestDTO to verify for only create  

      PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO) ; 
         return ResponseEntity.ok().body(patientResponseDTO);
     }

     @PutMapping("/{id}")
     @Operation(summary = "Update a Patient")
     @PreAuthorize("hasAuthority('UPDATE_PATIENT')")
     public ResponseEntity<PatientResponseDTO> updatePatient
     (@PathVariable String id, 
      @RequestBody PatientRequestDTO patientRequestDTO) {   

      //validation only for PatientRequest not registered date 
                                       
         PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO) ;                               
         return ResponseEntity.ok().body(patientResponseDTO);  
     }
     
     @DeleteMapping("/{id}")
     @Operation(summary = "Delete a Patient")
     @PreAuthorize("hasAuthority('DELETE_PATIENT')")
     public ResponseEntity<Void> deletePatient (@PathVariable String id){
         patientService.deletePatient(id);
        return ResponseEntity.noContent().build() ; 
     }

     // 1-Aug-2025

     @PostMapping("/addcase/{id}")
     @PreAuthorize("hasAuthority('ADD_CASE_BY_ID')")
     public ResponseEntity<?> addCase(@PathVariable String id  , @RequestBody VisitCase visitCase) {

         return ResponseEntity.ok().body(patientService.addVisitCase(id , visitCase));
     }

     @GetMapping("/gethistory/{id}")
     @PreAuthorize("hasAuthority('READ_PATIENT_HISTORY')")
     public ResponseEntity<?> getHistory(@PathVariable String id) {

        return ResponseEntity.ok(patientService.getVisitHistory(id));
     }
     

     @PostMapping("/assignTestResult/{department_id}/{case_id}")
     @PreAuthorize("hasAuthority('ASSIGN_TESTRESULT')")
     public ResponseEntity<?> assignTestResult(@PathVariable Long department_id , @PathVariable Long case_id) {
         return ResponseEntity.ok(patientService.assignTestResult(department_id , case_id));
     }
     
     @GetMapping("/getTestResult/{patient_id}/{case_id}")
     @PreAuthorize("hasAuthority('READ_TESTRESULT_FOR_CASE')")
     public ResponseEntity<?> getTestResult(@PathVariable String patient_id, @PathVariable Long case_id) {
        
         return ResponseEntity.ok(patientService.getTestResult(patient_id, case_id));
     }

     @PutMapping("updateTestResult/{testResult_id}")
     @PreAuthorize("hasAuthority('UPDATE_TESTRESULT')")
     public ResponseEntity<?> updateTestResult(@PathVariable Long testResult_id,  
                                              @RequestBody Map<String,String> resultMessage ) {

         return ResponseEntity.ok(patientService.updateTestResult(testResult_id  , resultMessage));
     }
     
     @GetMapping("/getAllDepartments")
     @PreAuthorize("hasAuthority('GET_ALL_DEPT')")
     public ResponseEntity<?> getAllDepartments() {
         return ResponseEntity.ok(patientService.getAllDepartments());
     }

     @GetMapping("/getAssignedCases/{dept_id}")
     @PreAuthorize("hasAuthority('GET_ASSIGNED_CASES')")
     public ResponseEntity<?> getAssignedCases(@PathVariable Long dept_id) {
         return ResponseEntity.ok(  patientService.getAssignedCases(dept_id) );
     }
     
     
     
}
