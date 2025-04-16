package com.app.car.rental.services.auth;

import com.app.car.rental.dto.SignupRequest;
import com.app.car.rental.dto.UserDto;

public interface AuthService {

	UserDto createCustomer(SignupRequest signupRequest);

	boolean hasCustomerWithEmail(String email);

}
