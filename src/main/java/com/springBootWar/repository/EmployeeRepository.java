package com.springBootWar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.springBootWar.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>, EmployeeRepositoryCustom {


}
