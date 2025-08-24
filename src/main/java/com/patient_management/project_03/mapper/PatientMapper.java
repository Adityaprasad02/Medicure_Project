package com.patient_management.project_03.mapper;

import java.time.LocalDate;
import java.util.List;

import com.patient_management.project_03.dto.HistoryDTO;
import com.patient_management.project_03.dto.PatientRequestDTO;
import com.patient_management.project_03.dto.PatientResponseDTO;
import com.patient_management.project_03.dto.ResultDTO;
import com.patient_management.project_03.model.Patient;
import com.patient_management.project_03.model.TestResult;
import com.patient_management.project_03.model.VisitCase;

public class PatientMapper {
   // return in form of DTO (JSON) -> @GetMapping
 public static PatientResponseDTO Todto(Patient patient){
    PatientResponseDTO patientResponseDTO = new PatientResponseDTO() ; 
    patientResponseDTO.setId(patient.getId());
    patientResponseDTO.setName(patient.getName());
    patientResponseDTO.setAddress(patient.getAddress());
    patientResponseDTO.setEmail(patient.getEmail());
    patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
    patientResponseDTO.setRegisteredDate(patient.getRegisteredDate().toString());
    return patientResponseDTO ; 
 }

 // return in form of Entity so to map with database -> @PostMapping
 public static Patient toModel(PatientRequestDTO patientRequestDTO , String toSet) {
    Patient patient = new Patient() ; 
    patient.setId(toSet);
    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
    return patient ; 
 }

 public static List<HistoryDTO> toHistoryDTO(List<VisitCase> history , String id) {

    List<HistoryDTO> historyDTOList = history.stream().map(visitCase -> {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setPatientId(id);
        historyDTO.setCaseId(visitCase.getCaseId());
        historyDTO.setVisitDate(visitCase.getVisitDate().toString());
        historyDTO.setVisitPurpose(visitCase.getVisitPurpose());
        historyDTO.setValidTill(visitCase.getValidTill().toString());
        historyDTO.setDoctorName(visitCase.getDoctorName());
        historyDTO.setDepartment(visitCase.getDepartment());
        return historyDTO;
    }).toList();
   
   return historyDTOList ;
 }

 public static List<ResultDTO> toresultDTO(List<TestResult> testResults) {
   List<ResultDTO> resultDTOList = testResults.stream().map(testResult -> {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResultId(testResult.getTestResultId());
        resultDTO.setPatientId(testResult.getPatientId());
        resultDTO.setCaseId(testResult.getVisitCase().getCaseId());
        resultDTO.setDepartmentName(testResult.getDepartmentName());
        resultDTO.setHeadOfDepartment(testResult.getHeadOfDepartment());
        resultDTO.setResultMessage(testResult.getResultMessage());
        resultDTO.setTestdepartmentId(testResult.getTestDepartmentId());
        return resultDTO;
    }).toList();

    return resultDTOList;
 }
}
