package com.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emp.model.Emp;
import com.emp.service.EmpService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class EmpController {

	@Autowired
	private EmpService service;
	
	//loading login page
	@GetMapping("/")
	public String loginPage() {
		return "login";
	}
	
	//load registration page
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("emp",new Emp());
		return "register";
	}
	//save registration data4
	@PostMapping("/register")
	public String registerEmp(@ModelAttribute Emp emp) {
		service.register(emp);
		return"redirect:/";
	}
	
	//login Logic
	@PostMapping("/login")
	public String login(@RequestParam String email,
			@RequestParam String password,
			HttpSession session,
			Model model) {
		//validate emp from db
		Emp e=service.login(email, password);
		
		if(e!=null) {
			//create session
			session.setAttribute("emplogged", e);
			return"redirect:/dashboard";
		}else {
			//login failed
			model.addAttribute("error","invalid Email or Password");
			return "login";
		}
	}
	//dashboard with session check
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,Model model) {
		Emp e=(Emp)session.getAttribute("emplogged");
		
		//if session expired or not logged in
		if(e==null) {
			return"redirect:/";
		}
		model.addAttribute("emp",e);
		return"dashboard";
	}
	//logout logic
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}