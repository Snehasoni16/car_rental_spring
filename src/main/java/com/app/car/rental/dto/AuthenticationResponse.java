package com.app.car.rental.dto;

import com.app.car.rental.util.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwt;

	private UserRole userRole;

	private Long userId;

}
