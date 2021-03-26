package com.nagarro.connection.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.nagarro.connection.model.Employee;
import com.nagarro.connection.service.EmployeeConnector;

@Controller
public class EmployeeDetailsController {

	final static Logger LOG = Logger.getLogger(EmployeeDetailsController.class);

	@Autowired
	private EmployeeConnector empMangService;
	
	@GetMapping("/")
	public String LetStart() {
		return "redirect:login";
	}

	@GetMapping("/employeedetail")
	public String getDetails(Model model) {
		List<Employee> dataAll = empMangService.getEmployees();
		model.addAttribute("searchResult", dataAll);
		model.addAttribute("hrmId", "Aditya");
		return "employeedetail";
	}

	@PostMapping("/employeedetail")
	public String getAllDetails() {
		return "employeedetail";
	}

	@GetMapping("/edit")
	public String editEmployee() {
		return "edit";
	}

	@RequestMapping("Update")
	public String uploadEmployee(@RequestParam("empCode") String empCode, @RequestParam("empName") String empName,
			@RequestParam("empLoc") String empLoc, @RequestParam("empMail") String empMail,
			@RequestParam("empDOB") String empDOB, @RequestParam("hrmId") String hrmId) {
		LOG.info("Recieved Employee Upload Request by : " + hrmId + " for employee : " + empName);

		// System.out.println(empDOB);
		Employee emp = empMangService.getEmployee(Integer.parseInt(empCode));
		emp.setEmail(empMail);
		emp.setEmployeeName(empName);
		emp.setLocation(empLoc);

		empMangService.updateEmployee(emp);

		return "redirect:employeedetail";
	}

	@RequestMapping("EditEmployee")
	public String EditEmployee(@RequestParam("empCode") String empCode, @RequestParam("hrmId") String hrmId,
			Model model) {
		LOG.info("Recieved Employee Edit Request by : " + hrmId + " for employee : " + empCode);
		Employee emp = empMangService.getEmployee(Integer.parseInt(empCode));
		model.addAttribute("empCode", emp.getEmployeeId());
		model.addAttribute("empDOB", emp.getDateOfBirth());
		model.addAttribute("empMail", emp.getEmail());
		model.addAttribute("empLoc", emp.getLocation());
		model.addAttribute("empName", emp.getEmployeeName());
		return "edit";

	}

	@RequestMapping("Download")
	public String downloadEmployeeList() {
		LOG.info("Recieved Download Employees Request. ");

		List<Employee> employees = empMangService.getEmployees();

		try (PrintWriter writer = new PrintWriter(new File("employees.csv"))) {
			StringBuilder sb = new StringBuilder();
			sb.append("employeeId, ");
			sb.append("employeeName, ");
			sb.append("location, ");
			sb.append("email, ");
			sb.append("dateOfBirth, ");
			sb.append('\n');

			for (Employee data : employees) {
				sb.append(data.getEmployeeId() + ",");
				sb.append(data.getEmployeeName() + ",");
				sb.append(data.getLocation() + ",");
				sb.append(data.getEmail() + ",");
				sb.append(data.getDateOfBirth() + ",");
				sb.append('\n');
			}

			writer.write(sb.toString());

			System.out.println("done!");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		LOG.info("Download employees successfully processed.");
		return "redirect:employeedetail";
	}
}
