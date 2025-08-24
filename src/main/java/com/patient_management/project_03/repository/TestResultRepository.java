package com.patient_management.project_03.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.patient_management.project_03.model.TestResult;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {


List<TestResult> findByVisitCaseCaseId(Long caseId);

boolean existsByVisitCaseCaseIdAndTestDepartmentId(Long case_id, Long department_id);

TestResult findBytestResultId(Long testResult_id);

List<TestResult> testDepartmentId(Long dept_id);



  

}
