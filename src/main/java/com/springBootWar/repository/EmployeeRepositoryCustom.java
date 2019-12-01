package com.springBootWar.repository;


import java.util.List;

import javax.persistence.StoredProcedureQuery;

import com.springBootWar.entity.Employee;

public interface EmployeeRepositoryCustom {

    List<Employee> getAllEmployees();
    
    StoredProcedureQuery getAllEmployeesString();
}
