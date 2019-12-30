package com.springBootWar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springBootWar.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Repository
public class ProcedureRepository {

	@Autowired
	EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String callEmployeeThroughProcedure(int dept_id) {

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
			emp.setEmp_dept_id((BigDecimal) array[2]);
			emp.setEmp_loc((String) array[3]);
			emp.setEmp_sal((BigDecimal) array[4]);
			empList.add(emp);
		}

		System.out.println(empList);

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(formatter.format(ts));
		String fileName = formatter.format(ts) + "_ExcludeFile.txt";

		Iterator<Employee> empIterator = empList.iterator();
		List empJsonList = new ArrayList<>();

		for (int i = 0; i < empList.size(); i++) {
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

		System.out.println("jsonMap= " + jsonMap);

		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		;
		String filePath = "D:\\InfyProject\\readFile\\" + fileName;

		try {
			mapper.writeValue(new File(filePath), jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return filePath;
	}

	/*
	 * @SuppressWarnings("unchecked") public void downloadFileWithKey(String
	 * fileKeyName) throws UnsupportedEncodingException {
	 * List<HttpMessageConverter<?>> messageConverters = new
	 * ArrayList<HttpMessageConverter<?>>(); messageConverters.add(new
	 * ByteArrayHttpMessageConverter());
	 * 
	 * RestTemplate restTemplate = new RestTemplate(messageConverters);
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Authorization",
	 * "Basic bml0aXNoOmtyaXNobmE="); HttpEntity<String> entity = new
	 * HttpEntity<String>(headers);
	 * 
	 * ResponseEntity<byte[]> response = null; try { response =
	 * restTemplate.exchange( "http://localhost:8082/downloadFile/"+fileKeyName,
	 * HttpMethod.GET, entity, byte[].class); }catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * byte[] bytes = response.getBody(); String str = new String(bytes, "UTF-8");
	 * 
	 * System.out.println(str);
	 * 
	 * String lines[] = str.split("\\r?\\n");
	 * 
	 * // step two : convert String array to list of String List<String>
	 * fixedLenghtList = Arrays.asList(lines);
	 * 
	 * // step three : copy fixed list to an ArrayList ArrayList<String>
	 * listOfString = new ArrayList<String>(fixedLenghtList);
	 * 
	 * listOfString.removeIf(s ->
	 * s.equalsIgnoreCase("#_#_This new Line can be ignored_#__#"));
	 * listOfString.removeIf(s -> s.equalsIgnoreCase("#_#Table_Name#_#"));
	 * listOfString.removeIf(s ->
	 * s.equalsIgnoreCase("#_#_This new Line can be can be started from here_#__#"))
	 * ; listOfString.removeIf(s ->
	 * s.equalsIgnoreCase("#_#_This new Line can be can be END from here_#__#"));
	 * 
	 * List rocket = new ArrayList<>(); List karoke = new ArrayList<>(); List value
	 * = new ArrayList<>();
	 * 
	 * for(int i=0; i<listOfString.size();i++) { String[] arr =
	 * listOfString.get(i).split("#_#"); rocket.add(arr[0]); karoke.add(arr[1]); try
	 * { value.add(arr[2]); }catch (Exception e) { value.add("");
	 * e.printStackTrace(); } } now in the reverce way
	 * 
	 * 
	 * final Iterator<String> i1 = rocket.iterator(); final Iterator<String> i2 =
	 * karoke.iterator(); final Iterator<String> i3 = value.iterator(); final
	 * List<String> combined = new ArrayList<>();
	 * 
	 * while (i1.hasNext() && i2.hasNext()) { combined.add(i1.next() +"#_#"+
	 * i2.next()+"#_#"+ i3.next()); }
	 * 
	 * List serializableList = new ArrayList<>();
	 * serializableList.add("#_#_This new Line can be ignored_#__#");
	 * serializableList.add("#_#Table_Name#_#");
	 * serializableList.add("#_#_This new Line can be can be started from here_#__#"
	 * ); Iterator<String> combined1 = combined.iterator();
	 * 
	 * while (combined1.hasNext()) { serializableList.add(combined1.next()); }
	 * 
	 * serializableList.add("#_#_This new Line can be can be END from here_#__#");
	 * 
	 * Serializing the list to a file String serializableString = (String)
	 * serializableList.stream().collect(Collectors.joining("\n"));
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); String filePath =
	 * "D:\\InfyProject\\readFile\\nvk.txt";
	 * 
	 * try { Files.write(Paths.get(filePath), serializableString.getBytes()); }
	 * catch (IOException e) { e.printStackTrace(); }
	 * 
	 * System.out.println(listOfString); }
	 */

	@SuppressWarnings("unchecked")
	public void downloadFileWithKey(String fileKeyName) throws UnsupportedEncodingException, CharacterCodingException {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new ByteArrayHttpMessageConverter());

		RestTemplate restTemplate = new RestTemplate(messageConverters);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic bml0aXNoOmtyaXNobmE=");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<byte[]> response = null;
		try {
			response = restTemplate.exchange("http://localhost:8082/downloadFile/" + fileKeyName, HttpMethod.GET,
					entity, byte[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] bytes = response.getBody();
		StringBuilder result = getStringBuilderObj(bytes);
		System.out.println(result.substring(2));

		StringTokenizer st = new StringTokenizer(result.toString(), System.lineSeparator());

		// step two : convert String array to list of String
		List fixedLenghtList = new ArrayList<>();

		while (st.hasMoreTokens() ) {
			
			try {
				System.out.println(st.nextToken());
				StringBuilder builder = new StringBuilder(st.nextToken().toString());
				fixedLenghtList.add(builder);
				// builder.append(st.nextToken().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// step three : copy fixed list to an ArrayList

		fixedLenghtList.removeIf(s -> s.equals("#_#_This new Line can be ignored_#__#".toString()));
		fixedLenghtList.removeIf(s -> s.equals("#_#Table_Name#_#"));
		fixedLenghtList.removeIf(s -> s.equals("#_#_This new Line can be can be started from here_#__#"));
		fixedLenghtList.removeIf(s -> s.equals("#_#_This new Line can be can be END from here_#__#"));

		List rocket = new ArrayList<>();
		List karoke = new ArrayList<>();
		List value = new ArrayList<>();

		/*
		 * for(int i=0; i<listOfString.size();i++) { String[] arr =
		 * listOfString.get(i).split("#_#"); rocket.add(arr[0]); karoke.add(arr[1]); try
		 * { value.add(arr[2]); }catch (Exception e) { value.add("");
		 * e.printStackTrace(); } }
		 */
		/* now in the reverce way */

		final Iterator<String> i1 = rocket.iterator();
		final Iterator<String> i2 = karoke.iterator();
		final Iterator<String> i3 = value.iterator();
		final List<String> combined = new ArrayList<>();

		while (i1.hasNext() && i2.hasNext()) {
			combined.add(i1.next() + "#_#" + i2.next() + "#_#" + i3.next());
		}

		List serializableList = new ArrayList<>();
		serializableList.add("#_#_This new Line can be ignored_#__#");
		serializableList.add("#_#Table_Name#_#");
		serializableList.add("#_#_This new Line can be can be started from here_#__#");
		Iterator<String> combined1 = combined.iterator();

		while (combined1.hasNext()) {
			serializableList.add(combined1.next());
		}

		serializableList.add("#_#_This new Line can be can be END from here_#__#");

		/* Serializing the list to a file */
		String serializableString = (String) serializableList.stream().collect(Collectors.joining("\n"));

		ObjectMapper mapper = new ObjectMapper();
		String filePath = "D:\\InfyProject\\readFile\\nvk.txt";

		try {
			Files.write(Paths.get(filePath), serializableString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println(listOfString);
	}

	private static StringBuilder getStringBuilderObj(byte[] src) throws CharacterCodingException {
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder decoder = charset.newDecoder();
		ByteBuffer srcBuffer = ByteBuffer.wrap(src);
		CharBuffer resBuffer = decoder.decode(srcBuffer);
		StringBuilder builder = new StringBuilder(resBuffer);
		return builder;
	}

}