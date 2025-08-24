package com.patient_management.project_03.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patient_management.project_03.Security.model.Admins;
import com.patient_management.project_03.Security.model.Principal;
import com.patient_management.project_03.Security.repository.adminRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private adminRepo adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admins admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        UserDetails userDetails = new Principal(admin);
        return userDetails;
    }

}
