package com.app.car.rental.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.car.rental.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// use to load username from the database
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

//	@Override
//	public UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		return new UserDetailsService() {
//
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				// TODO Auto-generated method stub
//				return userRepository.findFirstByEmail(username)
//						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//			}
//		};
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findFirstByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
