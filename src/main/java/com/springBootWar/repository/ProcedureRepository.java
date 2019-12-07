package com.springBootWar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springBootWar.entity.Employee;
import com.springBootWar.model.ProcedureResult;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProcedureRepository {

	@Autowired
	EntityManager entityManager;

	public ProcedureResult callEmployeeThroughProcedure(int dept_id) {

		StoredProcedureQuery proc = entityManager.createStoredProcedureQuery("EMPLOYEESPROCEDURE");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Employee.class, ParameterMode.REF_CURSOR);
		proc.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, dept_id);
		System.out.println(proc.getParameters().toArray());
		proc.execute();
		List<Object[]> resultList = (List<Object[]>) proc.getResultList();
		List<Employee> empList = new ArrayList<Employee>();

		for (Object[] array : resultList) {
			Employee emp = new Employee();
			emp.setEmp_id((BigDecimal) array[0]);
			emp.setEmp_name((String) array[1]);
			emp.setEmp_dept_id( (BigDecimal) array[2]);
			emp.setEmp_loc((String) array[3]);
			emp.setEmp_sal((BigDecimal) array[4]);
			empList.add(emp);
		}
		
		System.out.println(empList);

		return ProcedureResult.builder().employee((Employee) proc.getOutputParameterValue(2))
				.row_count((Integer) proc.getOutputParameterValue(3)).build();
	}

	public ProcedureResult callEmployeeThroughNamedStoredProcedureQuery(int dept_id) {

		StoredProcedureQuery proc = entityManager
				.createNamedStoredProcedureQuery("addEmployeeThroughNamedStoredProcedureQuery");
		proc.setParameter("DEPT_ID", dept_id);

		proc.execute();

		return ProcedureResult.builder().employee((Employee) proc.getOutputParameterValue("A_SELECT_REC_CUR"))
				.row_count((Integer) proc.getOutputParameterValue("A_ROW_COUNT")).build();
	}
}