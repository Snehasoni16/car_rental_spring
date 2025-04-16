package com.app.car.rental.services.admin;

import java.io.IOException;
import java.util.List;

import com.app.car.rental.dto.BookACarDto;
import com.app.car.rental.dto.CarDto;
import com.app.car.rental.dto.CarDtoListDto;
import com.app.car.rental.dto.SearchCarDto;

public interface AdminService {

	boolean postCar(CarDto carDto) throws IOException;

	List<CarDto> getAllCars();

	void deleteCar(Long id);

	CarDto getCarById(Long id);

	boolean updateCar(Long carId, CarDto carDto) throws io.jsonwebtoken.io.IOException, IOException;

	List<BookACarDto> getBookings();

	boolean changeBookingStatus(Long bookingId, String status);

	CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
