package com.springBootWar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springBootWar.entity.Employee;
import com.springBootWar.model.ProcedureResult;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
		
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");  
        System.out.println(formatter.format(ts));
		String fileName = formatter.format(ts)+"_ExcludeFile.txt";
		
		Iterator<Employee> empIterator = empList.iterator();
		List empJsonList = new ArrayList<>();
		
		for(int i=0; i< empList.size();i++) {
			Map empMap = new HashMap<>();
			empMap.put("Emp_ID", empList.get(i).getEmp_id());
			empMap.put("Emp_name", empList.get(i).getEmp_name());
			empMap.put("Emp_location", empList.get(i).getEmp_loc());
			empJsonList.add(empMap);
		}
		
		Map jsonMap = new LinkedHashMap<>();
		jsonMap.put("barID", "258651");
		jsonMap.put("FileName", fileName);
		jsonMap.put("Details", empJsonList);
		
		System.out.println("jsonMap= "+jsonMap);
		
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
		
		 try {
	            mapper.writeValue(new File("D:\\InfyProject\\writingFile\\"+fileName), jsonMap);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

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