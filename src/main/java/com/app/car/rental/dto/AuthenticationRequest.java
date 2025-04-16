package com.app.car.rental.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String email;
	private String password;

	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
}
