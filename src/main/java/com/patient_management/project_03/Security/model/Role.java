package com.patient_management.project_03.Security.model;

import java.util.Set;


public enum Role {

    OFFICE_ADMIN(Set.of(Permissions.CREATE_PATIENT, Permissions.READ_ALL_PATIENT, 
    Permissions.UPDATE_PATIENT, Permissions.DELETE_PATIENT, Permissions.ADD_CASE_BY_ID,
    Permissions.READ_PATIENT_HISTORY, Permissions.READ_PATIENT_BY_ID,Permissions.READ_TESTRESULT_FOR_CASE
    ,Permissions.ASSIGN_TESTRESULT , Permissions.GET_ALL_DEPT , Permissions.GET_ALL_ADMIN )),

    DEPARTMENT_ADMIN(Set.of(Permissions.UPDATE_TESTRESULT , Permissions.GET_ASSIGNED_CASES , Permissions.GET_ALL_ADMIN)),

    PATIENT(Set.of(Permissions.READ_PATIENT_HISTORY , Permissions.READ_TESTRESULT_FOR_CASE));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
  
}
