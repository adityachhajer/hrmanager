package com.nagarro.connection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nagarro.connection.model.Employee;
import com.nagarro.connection.service.EmployeeConnector;

@Controller
public class uploadEmployee {

	@GetMapping(value = "/upload")
	public String getData() {
		return "upload";
	}

	@Autowired
	private EmployeeConnector empMangService;

	@SuppressWarnings("finally")
	@PostMapping(value = "/upload")
	public String uploadEmployee(@RequestParam("empName") String empName, @RequestParam("empLoc") String empLoc,
			@RequestParam("empMail") String empMail, @RequestParam("empDOB") String empDOB,
			@RequestParam("hrmId") String hrmId, Model model) {

		Employee emp = new Employee();
		emp.setEmployeeName(empName);
		emp.setLocation(empLoc);
		emp.setEmail(empMail);
		emp.setDateOfBirth(empDOB);
		try {
			empMangService.addEmployee(emp);
		} catch (Exception e) {
			System.out.println("Data is successfully added..!");
		} finally {
			List<Employee> employees = empMangService.getEmployees();
			model.addAttribute("searchResult", employees);
			model.addAttribute("hrmId", "Aditya");
			return "employeedetail";
		}

	}

}
