package com.patient_management.project_03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
   String patientId ;
   Long caseId ;
   String visitDate ; 
   String visitPurpose ;
   String validTill ;
   String doctorName;
   String department;
}
