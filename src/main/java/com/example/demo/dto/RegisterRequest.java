package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	@Email(message = "Invalid email format")
	@NotNull(message = "Email cannot be null")
	private String email;
	@NotNull(message = "Pwd cannot be null")
	@Size(min=8,message = "Password must be atleast 8 characters long")
	private String pwd;
	@NotNull(message = "Balance cannot be null")
	@PositiveOrZero(message = "Balance must be zero or positive")
	private Double balance;

}
