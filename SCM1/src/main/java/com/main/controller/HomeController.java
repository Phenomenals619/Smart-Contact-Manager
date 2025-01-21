package com.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.dao.UserRepository;
import com.main.entities.User;
import com.main.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	
	@RequestMapping("/about")
	public String about() {
		
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1, @RequestParam(value="agreement",defaultValue = "false") Boolean agreement, Model model, HttpSession session) {
		try {
			
			if(!agreement) {
				System.out.println("not agree the terms and conditions");
				throw new Exception("not agree the terms and conditions");
				
			}
			
			if(result1.hasErrors()) {
				System.out.println("ERROR"+ result1.toString());
				model.addAttribute("user",user);
				return "signUp";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement"+agreement);
			System.out.println(user);
			
			User result=userRepository.save(user);
			
			
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Register Successfully", "alert-success"));
			return "redirect:/signup";
			
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			 
			session.setAttribute("message", new Message("something went wrong !!"+e.getMessage(), "alert-danger"));
			return "redirect:/signup";
		}
		
		
	}
	
	
	
	

}
