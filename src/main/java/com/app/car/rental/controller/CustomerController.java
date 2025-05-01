package com.app.car.rental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.car.rental.dto.BookACarDto;
import com.app.car.rental.dto.CarDto;
import com.app.car.rental.dto.SearchCarDto;
import com.app.car.rental.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor

public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("/cars")
	public ResponseEntity<List<CarDto>> getAllCars() {
		List<CarDto> carDtoList = customerService.getAllCars();
		return ResponseEntity.ok(carDtoList);
	}

	@PostMapping("/car/book")
	public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookCarDto) {
		boolean success = customerService.bookACar(bookCarDto);
		if (success) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}

	@GetMapping("/car/{carId}")
	public ResponseEntity<CarDto> getCarById(@PathVariable Long carId) {
		CarDto carDto = customerService.getCarById(carId);
		if (carDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carDto);

	}

	@GetMapping("/car/bookings/{userId}")
	public ResponseEntity<List<BookACarDto>> getBookingUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
	}

	// searching Api
	@PostMapping("/car/search")
	public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
		return ResponseEntity.ok(customerService.searchCar(searchCarDto));
	}

}
