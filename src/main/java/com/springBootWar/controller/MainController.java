package com.springBootWar.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springBootWar.entity.Employee;
import com.springBootWar.model.ProcedureResult;
import com.springBootWar.repository.ProcedureRepository;
import com.springBootWar.request.Request;

@RestController
public class MainController {

	@Autowired
    private ProcedureRepository procedureRepository;

	@PostMapping(value = "/hi")
	public ResponseEntity<?> demo(@RequestBody Request request) {

		if (request.getReqType().trim().equalsIgnoreCase("PING")) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		if (request.getReqType().trim().equalsIgnoreCase("input")) {

			CompletableFuture.runAsync(() -> {
				callStoredProcedure();
			});

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void callStoredProcedure() {
		Employee emp = new Employee();
        ProcedureResult procedureResult = null;
        try {
        	procedureResult = procedureRepository.callEmployeeThroughProcedure(1);
        }catch (Exception e) {
			e.printStackTrace();
		}
        //emp = procedureResult.getEmployee();
        int rowCount = procedureResult.getRow_count();

        System.out.println("Employee : " + emp);
        System.out.println("rowCount : " + rowCount);
	}

}
