package com.patient_management.project_03.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patient_management.project_03.Security.DTOS.AdminDTO;
import com.patient_management.project_03.Security.JWTutil.JWTService;
import com.patient_management.project_03.Security.model.Admins;
import com.patient_management.project_03.Security.repository.adminRepo;
import com.patient_management.project_03.model.TestDepartments;
import com.patient_management.project_03.repository.DepartmentRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Service
public class AdminService {

    @Autowired
    adminRepo adminRepository;
    
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService; 

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public Admins registerAdmin(AdminDTO adminDTO) {
         
        if(adminRepository.existsByUsername(adminDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if(adminDTO.getRole().name().equals("DEPARTMENT_ADMIN") && adminDTO.getDeptId() == null) {
            throw new RuntimeException("Department ID is required for DEPARTMENT_ADMIN role");
        }

        if(adminDTO.getRole().name().equals("DEPARTMENT_ADMIN")
                                           && departmentRepository.findById(adminDTO.getDeptId()).isEmpty()) {

            throw new RuntimeException("Department not found with ID: " + adminDTO.getDeptId());
        }        
        
         TestDepartments departments ;
        // for offce_admin dept_id not required and can be null
        if(adminDTO.getRole().name().equals("OFFICE_ADMIN") && adminDTO.getDeptId() == null) {
            departments = null ; 
        } else {
            departments = departmentRepository.findById(adminDTO.getDeptId()).get() ; 
        }

        Admins admin = new Admins();
        admin.setUsername(adminDTO.getUsername());
        admin.setRole(adminDTO.getRole());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setTestDepartment(departments) ; 
   
        return adminRepository.save(admin);
    }

    public String loginAdmin(AdminDTO adminDTO) {
        // convert the DTO to an entity
        Admins admin = adminRepository.findByUsername(adminDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + adminDTO.getUsername()));

        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(adminDTO.getUsername(), adminDTO.getPassword())
        );

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(admin);
        } else {
            // Handle login failure
            throw new RuntimeException("Invalid username or password");
        }
    }

            public Admins getAdminByUsername(String username) {
       Admins adminDetail =  adminRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Admin Not Found with Username: " + username));

       return adminDetail;
    }


    


}
