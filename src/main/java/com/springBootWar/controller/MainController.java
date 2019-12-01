package com.springBootWar.controller;

import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springBootWar.repository.EmployeeRepository;

@RestController
public class MainController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/get")
	public StoredProcedureQuery getAllEmployees() {

		StoredProcedureQuery employeeString = employeeRepository.getAllEmployeesString();

		System.out.println(employeeString.toString());

		return employeeString;

	}

	@GetMapping("/hi")
	public String demo() {
		return "hello";
	}

}
