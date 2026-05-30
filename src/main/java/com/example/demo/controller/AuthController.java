package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Customer;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
     private AuthService service;
     private AuthenticationManager authenticationManager;
     @Autowired
	public AuthController(AuthService service, AuthenticationManager authenticationManager) {
		super();
		this.service = service;
		this.authenticationManager = authenticationManager;
	}
     @PostMapping("/register")
     public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request)
     {
    	 System.out.println("Controller hit");
    	 service.registerCustomer(request.getEmail(), request.getPwd(), request.getBalance());
    	 return ResponseEntity.ok("Customer registered successfully");
    	 
     }
     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody Map<String, String> req)
     {
    	 String email=req.get("email");
    	 String pwd=req.get("password");
    	 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,pwd));
    	 String token=service.generateToken(email);
    	 return ResponseEntity.ok(Map.of("token",token));
     }
     
     @GetMapping("/account")
     public ResponseEntity<Customer> getAccountDetails()
     {
    	 String email=SecurityContextHolder.getContext().getAuthentication().getName();
    	 Customer customer=service.getCustomerDetails(email);
    	 return ResponseEntity.ok(customer);
     }
}
