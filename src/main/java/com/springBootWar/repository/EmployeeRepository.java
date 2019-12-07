package com.springBootWar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBootWar.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Double > {
	
}