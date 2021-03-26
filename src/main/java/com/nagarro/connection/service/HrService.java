package com.nagarro.connection.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HrService {

	Map<String, String> userDetails = new HashMap<>();

	public HrService() {
		super();
		userDetails.put("nagarro", "learning");
	}

	public boolean isValidUser(String userName, String Password) {
		if (userDetails.containsKey(userName) && userDetails.get(userName).equals(Password)) {
			return true;
		}
		return false;
	}

}
