package com.springBootWar.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springBootWar.entity.Employee;
import com.springBootWar.model.ProcedureResult;
import com.springBootWar.repository.ProcedureRepository;
import com.springBootWar.request.Request;
import com.springBootWar.service.FileUploadService;

@RestController
public class MainController {

	@Autowired
	private ProcedureRepository procedureRepository;

	@Autowired
	private FileUploadService fileUploadService;

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
		String filePath = null;
		try {
			filePath = procedureRepository.callEmployeeThroughProcedure(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileUploadService.postFile(filePath);
		System.out.println("Employee : " + emp);
	}

}
