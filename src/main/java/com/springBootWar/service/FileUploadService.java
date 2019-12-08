package com.springBootWar.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class FileUploadService {

	private RestTemplate restTemplate;

	@Autowired
	public FileUploadService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	public void postFile(String filePath) {
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		FileSystemResource value = new FileSystemResource(new File(filePath));
		map.add("file", value);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = null;
		try {
            response = restTemplate.exchange("http://localhost:8082/uploadFile", HttpMethod.POST, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
            e.printStackTrace();
        }  
		
		System.out.println("response:"+ response);
	}

}
