package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationDto {
	
	@NotNull(message = "is required")
	@Size(min = 3, message = "請輸入至少3位數的英文或數字")
	@NotNull (message = "is required")
    private String username;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern (regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$", message = "請輸入至少8位數的密碼，請包含至少一個大寫小寫與一個數字。")
    private String password;
	
	@NotNull(message = "is required")
	@Email
    private String email;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}