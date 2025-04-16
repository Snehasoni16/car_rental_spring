package com.app.car.rental.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.car.rental.dto.AuthenticationRequest;
import com.app.car.rental.dto.AuthenticationResponse;
import com.app.car.rental.dto.SignupRequest;
import com.app.car.rental.dto.UserDto;
import com.app.car.rental.entity.User;
import com.app.car.rental.repository.UserRepository;
import com.app.car.rental.services.auth.AuthService;
import com.app.car.rental.services.jwt.UserService;
import com.app.car.rental.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService authService;

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final UserRepository userRepository;
	private final JWTUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
		if (authService.hasCustomerWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("Customer already exits with this email", HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("Inside AuthController signupCustomer");
		UserDto createdCustomerDto = authService.createCustomer(signupRequest);
		if (createdCustomerDto == null) {
			return new ResponseEntity<>("Customer not created come again later", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws BadCredentialsException, DisabledException, UsernameNotFoundException {
//		try {
//
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
//					authenticationRequest.getPassword()));
//		} catch (BadCredentialsException e) {
//			e.printStackTrace();
//			throw new BadCrentialsException("Incorrect username or password");
//		}
		final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if (optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		}
		return authenticationResponse;

	}
}
