package com.patient_management.project_03.Security.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Principal implements UserDetails {

    private Admins admin;

    public Principal(Admins admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
           
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(() -> "ROLE_" + admin.getRole().name()); 

        for(Permissions permissions : admin.getRole().getPermissions()) {
            authorities.add(() -> permissions.name());
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

}
