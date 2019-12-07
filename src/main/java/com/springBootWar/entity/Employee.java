package com.springBootWar.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.NamedStoredProcedureQueries;


@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "getEmployeeThroughStoredProcedure", procedureName = "EMPLOYEESPROCEDURE", 
				resultClasses = Employee.class,
				parameters = {
				@StoredProcedureParameter(name = "DEPT_ID", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "A_SELECT_REC_CUR", mode = ParameterMode.REF_CURSOR, type = Employee.class),
				@StoredProcedureParameter(name = "A_ROW_COUNT", mode = ParameterMode.OUT, type = Integer.class), }) 
		})
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

	@Id
	//@Column(name = "EMP_ID")
	private BigDecimal  emp_id;

	//@Column(name = "EMP_NAME")
	private String emp_name;

	//@Column(name = "EMP_DEPT_ID")
	private BigDecimal  emp_dept_id;

	//@Column(name = "EMP_LOC")
	private String emp_loc;

	//@Column(name = "EMP_SAL")
	private BigDecimal  emp_sal;

	
	
	
}
