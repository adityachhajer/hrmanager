package com.nagarro.connection.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.nagarro.connection.Urls.URLType;
import com.nagarro.connection.model.Employee;

@Service
public class EmployeeConnector {

	final static Logger LOG = Logger.getLogger(EmployeeConnector.class);
	
	@Value("${api.employee.host:http://localhost:8080}")
	private String employeeServiceHost;
	
	private final  String apiPath = "/employees"; 
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Employee> getEmployees() {

		LOG.info("Requesting EmployeeRestService for list of employees.");

		//String url =  UriComponentsBuilder.fromUriString("/employees").build().toUriString();


		ResponseEntity<List<Employee>> response = restTemplate.exchange(employeeServiceHost+apiPath, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});

		List<Employee> employees = response.getBody();

		LOG.info("Request successful. Recieved list of employees.");
		return employees;

	}

	public Employee getEmployee(int empCode) {
		LOG.info("Requesting EmployeeRestService for employee : " + empCode);

		String url =  UriComponentsBuilder.fromUriString(employeeServiceHost+apiPath+"/{id}").buildAndExpand(empCode).toUriString();

		LOG.info("Request successful. Recieved employee : " + empCode);
		return restTemplate.getForObject(url, Employee.class);
	}

	public void addEmployee(Employee emp) {
		LOG.info("Requesting EmployeeRestService to add new employee : " + emp.getEmployeeId());

		String url = employeeServiceHost + apiPath;


		HttpEntity<Employee> request = new HttpEntity<Employee>(emp);
		restTemplate.postForObject(url, request, Employee.class);

		LOG.info("Request successful. Added employee : " + emp.getEmployeeId());
	}

	public void updateEmployee(Employee emp) {
		LOG.info("Requesting EmployeeRestService to update employee : " + emp.getEmployeeId());

		UriComponents url = UriComponentsBuilder.fromUriString(employeeServiceHost + apiPath + "/{id}").buildAndExpand(emp.getEmployeeId());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Employee> request = new HttpEntity<Employee>(emp);

		restTemplate.put(url.toUri(), request);
		LOG.info("Request successful. Updated employee : " + emp.getEmployeeId());
	}

}
