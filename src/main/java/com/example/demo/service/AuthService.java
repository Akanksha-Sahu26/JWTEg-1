package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.util.JwtUtil;


@Service
public class AuthService {
   private CustomerRepository repo;
   private PasswordEncoder encoder;
   @Autowired
   private JwtUtil jwtUtil;
   @Autowired
public AuthService(CustomerRepository repo, PasswordEncoder encoder) {
	super();
	this.repo = repo;
	this.encoder = encoder;
}
   public void registerCustomer(String email,String pwd,Double balance)
   {
	   System.out.println("Register");
	   if(repo.findByEmail(email).isPresent())
	   {
		   throw new IllegalArgumentException("Email already exists");
	   }
	   Customer cust=new Customer();
	   cust.setEmail(email);
	   cust.setPwd(encoder.encode(pwd));
	   cust.setRole("ROLE_USER");
	   cust.setBalance(balance);
	   repo.save(cust);
	   System.out.println("Register succesfully");
   }
   public Customer getCustomerDetails(String email)
   {
	  Customer customer=repo.findByEmail(email).orElse(null);
	  if(customer==null)
	  {
		  throw new IllegalArgumentException("Username not found");
      }
	  return  customer;
  }
   public String generateToken(String email)
   {
	   return jwtUtil.generateToken(email);
   }
   
   
}
