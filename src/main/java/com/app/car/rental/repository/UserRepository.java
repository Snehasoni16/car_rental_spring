package com.app.car.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.car.rental.entity.User;
import com.app.car.rental.util.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findFirstByEmail(String email);

//	User findByUserRole(UserRole userRole);

	User findByUserRole(UserRole userRole);

}
