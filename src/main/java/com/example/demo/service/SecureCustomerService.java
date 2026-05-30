package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class SecureCustomerService implements UserDetailsService{
	@Autowired
    private CustomerRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer=repo.findByEmail(username).orElse(null);
		if(customer==null)
		{
			throw new UsernameNotFoundException("User details not found for "+username);
		}
		List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority(customer.getRole()));
		return new User(username,customer.getPwd(),authorities);
	}

}
