package com.app.car.rental.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.car.rental.dto.BookACarDto;
import com.app.car.rental.dto.CarDto;
import com.app.car.rental.dto.SearchCarDto;
import com.app.car.rental.services.admin.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/car")
	public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
		boolean success = adminService.postCar(carDto);
		if (success) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/car")
	public ResponseEntity<?> getAllCars() {
		return ResponseEntity.ok(adminService.getAllCars());

	}

	// method for delete car
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
		adminService.deleteCar(id);
		return ResponseEntity.ok(null);

	}

	@GetMapping("/car/{id}")
	public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
		CarDto carDto = adminService.getCarById(id);
		return ResponseEntity.ok(carDto);

	}

	@PutMapping("/car/{carId}")
	public ResponseEntity<Void> updateCar(@PathVariable Long carId, @ModelAttribute CarDto carDto) throws IOException {
		log.info("Calling update car");
		try {
			boolean success = adminService.updateCar(carId, carDto);
			if (success) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	// api for get booking
	@GetMapping("/car/bookings")
	private ResponseEntity<List<BookACarDto>> getBookings() {
		return ResponseEntity.ok(adminService.getBookings());
	}

	@GetMapping("/car/booking/{bookingId}/{status}")
	public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status) {
		boolean success = adminService.changeBookingStatus(bookingId, status);
		if (success) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}

	// searching Api
	@PostMapping("/car/search")
	public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
		return ResponseEntity.ok(adminService.searchCar(searchCarDto));
	}
	// image code
//	@Autowired
//	private CarRepository carRepository; // 👈 Inject CarRepository instance
//
//	@PostMapping("/car")
//	public ResponseEntity<String> uploadCarImage(@RequestParam("file") MultipartFile file) {
//		try {
//			byte[] imageData = file.getBytes(); // 👈 Convert file to byte array
//			Car car = new Car();
//			car.setImage(imageData); // 👈 Save the image in the database
//			carRepository.save(car); // 👈 Call save() on the instance variable
//			return ResponseEntity.ok("Image uploaded successfully");
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
//		}
//	}

}
