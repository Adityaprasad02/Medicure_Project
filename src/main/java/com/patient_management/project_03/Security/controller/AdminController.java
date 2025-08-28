package com.patient_management.project_03.Security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.patient_management.project_03.Security.DTOS.AdminDTO;
import com.patient_management.project_03.Security.DTOS.AdminDTO.Login;
import com.patient_management.project_03.Security.DTOS.AdminDTO.Register;
import com.patient_management.project_03.Security.service.AdminService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://medicuregeneralhospital.netlify.app"})
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Value("${hospital.secret.key}")
    private String secretKey;



    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestHeader(value = "X-Hospital-Secret", required = false) String secretKey,
        @Validated(Register.class) @RequestBody AdminDTO adminDTO) {

            if(secretKey == null || !secretKey.equals(this.secretKey)) {
                return ResponseEntity.status(403).body("Forbidden: Invalid or missing secret key");
            }

            
         return ResponseEntity.ok(adminService.registerAdmin(adminDTO));
    }

  
@PostMapping("/admin/login")
public ResponseEntity<?> loginAdmin(@Validated(Login.class) @RequestBody AdminDTO adminDTO) {
    try {
        String token = adminService.loginAdmin(adminDTO);
        // Return JWT token in JSON body
        return ResponseEntity.ok(Map.of("token", token));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(Map.of("error", e.getMessage()));
    }
}

  @GetMapping("/getAdmin/{username}")
       @PreAuthorize("hasAuthority('GET_ALL_ADMIN')")
     public ResponseEntity<?> getAdmin(@PathVariable String username) { 
        return ResponseEntity.ok(adminService.getAdminByUsername(username));
     }

    
}


// Testing the endpoint with a sample JSON payload
// {
//   "username": "priya.sharma",
//   "password": "Delhi@2025",
//   "role": "OFFICE_ADMIN"
// }


// {
//   "username": "rajesh.verma",
//   "password": "Mumbai#123",
//   "role": "DEPARTMENT_ADMIN"
// }

// {
//   "username": "neha.patil",
//   "password": "Pune@2025",
//   "role": "DEPARTMENT_ADMIN",
//   "departmentId": 1002
// }

