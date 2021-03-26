package com.nagarro.connection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nagarro.connection.model.HrEmployee;
import com.nagarro.connection.service.HrService;

@Controller

public class HrManagerController {

	@Autowired
	private HrService service;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@ModelAttribute("user")
	public HrEmployee employeeController() {
		return new HrEmployee();
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute("user") HrEmployee employeeController) {
//		System.out.println(employeeController.getUserName());
//		System.out.println(employeeController.getPassword());
		if (service.isValidUser(employeeController.getUserName(), employeeController.getPassword())) {
			return "redirect:employeedetail";
		}
		return "redirect:login?error";

	}

//	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String loginUser(@RequestBody HrEmployee employeeController) {
//		System.out.println(employeeController.getUserName());
//		System.out.println(employeeController.getPassword());
//		if (service.isValidUser(employeeController.getUserName(), employeeController.getPassword())) {
//			return "redirect:login?success";
//		}
//		return "redirect:login?error";
//
//	}
}
