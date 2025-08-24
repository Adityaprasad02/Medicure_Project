package com.patient_management.project_03.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.patient_management.project_03.dto.HistoryDTO;
import com.patient_management.project_03.dto.PatientRequestDTO;
import com.patient_management.project_03.dto.PatientResponseDTO;
import com.patient_management.project_03.dto.ResultDTO;
import com.patient_management.project_03.exception.EmailAlreadyExistsException;
import com.patient_management.project_03.exception.PatientNotFoundException;
import com.patient_management.project_03.mapper.PatientMapper;
import com.patient_management.project_03.model.Patient;
import com.patient_management.project_03.model.TestDepartments;
import com.patient_management.project_03.model.TestResult;
import com.patient_management.project_03.model.VisitCase;
import com.patient_management.project_03.repository.DepartmentRepository;
import com.patient_management.project_03.repository.PatientRepository;
import com.patient_management.project_03.repository.TestResultRepository;
import com.patient_management.project_03.repository.VisitCaseRepository;

@Service
public class PatientService {

    private final DepartmentRepository departmentRepository;
     
    @Autowired
    private PatientRepository patientRepository ;

    @Autowired
    private VisitCaseRepository visitCaseRepository;

    @Autowired
    private TestResultRepository testResultRepository;



    PatientService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public String generateId() {
        Optional<Patient> lastPatient = patientRepository.findTopByOrderByIdDesc() ; 
        int next = 1 ; 
        if(lastPatient.isPresent()){
            String lastId = lastPatient.get().getId() ; 
            //ASC008  -> 008
            String numericPart = lastId.substring(3);  
            next = Integer.parseInt(numericPart) + 1 ; 
        }
        return String.format("ASC%03d", next); 
    }


    public List<PatientResponseDTO> getPatients() {

        // It will get in form of Entity but we have to return it in form of DTO
        List<Patient> patients = patientRepository.findAll() ; 

        List<PatientResponseDTO> patientResponseDTOs
         = patients.stream().map(patient -> PatientMapper.Todto(patient)).toList() ; 
        return patientResponseDTOs ; 
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A user with this Email Already Exists " 
            + patientRequestDTO.getEmail()) ; 
        }
           
        String toSet = generateId() ; 
          // It will get in form of DTO but we have to pass it in form of Entity for JPA
          Patient NewPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO,toSet)) ;  
          //Entity -> DTO
          return PatientMapper.Todto(NewPatient) ; 
    }
  
    public PatientResponseDTO updatePatient(String id , PatientRequestDTO patientRequestDTO){

        Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new PatientNotFoundException("Patient Not Found" + id)) ; 
            

            if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A user with this Email Already Exists " 
            + patientRequestDTO.getEmail()) ; 
        }
         // returns the entity where we have to update 

         // set the changes 
         if(patientRequestDTO.getName() != null){
        patient.setName(patientRequestDTO.getName());
         }
        
         if(patientRequestDTO.getAddress()!=null){
        patient.setAddress(patientRequestDTO.getAddress());
        }
        
         if(patientRequestDTO.getEmail()!=null){
        patient.setEmail(patientRequestDTO.getEmail());
        }
        
         if(patientRequestDTO.getDateOfBirth()!=null){
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        }

         
        // save it
         Patient updatedPatient = patientRepository.save(patient) ; 
          
          //entity -> DTO
          return PatientMapper.Todto(updatedPatient) ;
    }
   
    public void deletePatient(String id){
        patientRepository.deleteById(id); 
    }


    public VisitCase addVisitCase(String id , VisitCase visitCase) {
        Optional<Patient> findPatient = patientRepository.findById(id);
        if(findPatient.isEmpty()){
            throw new PatientNotFoundException("Patient Not Found with ID: " + id);
        }

        VisitCase newVisitCase = new VisitCase();

        newVisitCase.setPatient(findPatient.get());
        newVisitCase.setVisitPurpose(visitCase.getVisitPurpose());
        newVisitCase.setVisitDate(LocalDate.now().toString());
        newVisitCase.setValidTill(LocalDate.now().plusDays(3).toString());
        newVisitCase.setDoctorName(visitCase.getDoctorName());
        newVisitCase.setDepartment(visitCase.getDepartment());

            visitCaseRepository.save(newVisitCase);

        return newVisitCase; 
    }


    public List<HistoryDTO> getVisitHistory(String id) {

             Optional<Patient> findPatient = patientRepository.findById(id);
        if(findPatient.isEmpty()){
            throw new RuntimeException("Patient Not Found with ID: " + id);
        }
        if(visitCaseRepository.existsByPatientId(id) == false){
            throw new RuntimeException("No Visit History Found for Patient with ID: " + id);
        }

        List<VisitCase> history = visitCaseRepository.findByPatientId(id) ; 
        

        
        return PatientMapper.toHistoryDTO(history, id);
    }


    public PatientResponseDTO getPatientbyId(String id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new PatientNotFoundException("Patient Not Found with ID: " + id));
  
        return PatientMapper.Todto(patient);
    }


    public TestResult assignTestResult(Long department_id , Long case_id) {
        
        TestDepartments department = departmentRepository.findById(department_id)
            .orElseThrow(() -> new RuntimeException("Department Not Found with ID: " +  department_id));
            
        VisitCase visitCase = visitCaseRepository.findById(case_id)
                            .orElseThrow(() -> new RuntimeException("Visit Case Not Found with ID: " + case_id));


        // to check if already visitcase for a department exists
        if(testResultRepository.existsByVisitCaseCaseIdAndTestDepartmentId(case_id, department_id)) {
            throw new RuntimeException("Test Result already exists for this Visit Case in the specified department");
        }
        
            TestResult testResult = new TestResult();
           testResult.setTestDepartmentId(department_id);
           testResult.setDepartmentName(department.getDepartmentName());
           testResult.setHeadOfDepartment(department.getHeadOfDepartment());
           testResult.setResultMessage(null);
           testResult.setVisitCase(visitCase);
           testResult.setPatientId(visitCase.getPatient().getId());

           return testResultRepository.save(testResult);
    }


    public List<ResultDTO> getTestResult(String patient_id, Long case_id) {
           visitCaseRepository.findById(case_id)
            .orElseThrow(() -> new RuntimeException("Visit Case Not Found with ID: " + case_id));

        List<TestResult> testResults = testResultRepository.findByVisitCaseCaseId(case_id);

        if (testResults.isEmpty()) {
            throw new RuntimeException("No Test Results Found for Patient with ID: " + patient_id);
        }

      return PatientMapper.toresultDTO(testResults);
    }


    public TestResult updateTestResult(Long testResult_id , Map<String,String> resultMessage) {
                TestResult testResult = testResultRepository.findBytestResultId(testResult_id) ;
                String msg = resultMessage.get("result") ; 

                testResult.setResultMessage(msg);
                testResultRepository.save(testResult) ; 
              return testResult ; 
    }


    public List<TestDepartments> getAllDepartments() {

        return departmentRepository.findAll() ; 
           
    }


    public List<TestResult> getAssignedCases(Long dept_id) {
         
        List<TestResult> testResult = testResultRepository.testDepartmentId(dept_id) ;
        return testResult ;  
    }
 




}
