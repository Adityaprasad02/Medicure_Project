package com.patient_management.project_03.Security.DTOS;

import com.patient_management.project_03.Security.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDTO {

    // Fields for AdminDTO  
    @NotBlank      
    private String username;

    @NotBlank
    private String password;
    

    // for register role required 
    // but for login not required
    // we will use validation groups to handle this
    @NotNull(groups = Register.class)
    private Role role;
    

    // Additional field for Department ID if needed
    private Long deptId ;


    public interface Register {
    } 

    public interface Login {
    }
}
