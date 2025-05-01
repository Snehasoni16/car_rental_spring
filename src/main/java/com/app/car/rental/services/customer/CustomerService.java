package com.app.car.rental.services.customer;

import java.util.List;

import com.app.car.rental.dto.BookACarDto;
import com.app.car.rental.dto.CarDto;
import com.app.car.rental.dto.CarDtoListDto;
import com.app.car.rental.dto.SearchCarDto;

public interface CustomerService {

	List<CarDto> getAllCars();

	boolean bookACar(BookACarDto bookACarDto);

	CarDto getCarById(Long carId);

	List<BookACarDto> getBookingsByUserId(Long userId);

	CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
