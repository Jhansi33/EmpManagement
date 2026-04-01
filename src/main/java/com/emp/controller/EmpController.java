package com.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emp.model.Emp;
import com.emp.service.EmpService;

import java.util.Map;

@CrossOrigin(origins = "*") // Allows the new frontend on any port/origin to fetch data
@RestController
public class EmpController {

	@Autowired
	private EmpService service;
	
	//save registration data
	@PostMapping("/register")
	public ResponseEntity<?> registerEmp(@RequestBody Emp emp) {
		service.register(emp);
		return ResponseEntity.ok("Registered Successfully");
	}
	
	//login Logic
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
		String email = payload.get("email");
		String password = payload.get("password");
		
		//validate emp from db
		Emp e = service.login(email, password);
		
		if(e != null) {
			return ResponseEntity.ok(e); // Return user info as JSON
		} else {
			//login failed
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
		}
	}
}