package com.app.car.rental.services.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.app.car.rental.dto.BookACarDto;
import com.app.car.rental.dto.CarDto;
import com.app.car.rental.dto.CarDtoListDto;
import com.app.car.rental.dto.SearchCarDto;
import com.app.car.rental.entity.BookACar;
import com.app.car.rental.entity.Car;
import com.app.car.rental.repository.BookACarRepository;
import com.app.car.rental.repository.CarRepository;
import com.app.car.rental.util.BookCarStatus;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

	private final CarRepository carRepository;

	private final BookACarRepository bookACarRepository;

	@Override
	public boolean postCar(CarDto carDto) throws IOException {

		try {
			Car car = new Car();
			car.setName(carDto.getName());
			car.setBrand(carDto.getBrand());
			car.setColor(carDto.getColor());
			car.setPrice(carDto.getPrice());
			// Date date = Constant.getDateFormat().parse(carDto.getYear()); // Parse the
			// car.setYear(date);
			car.setYear(carDto.getYear());
			car.setType(carDto.getType());
			car.setDescription(carDto.getDescription());
			car.setTransmission(carDto.getTransmission());
			if (carDto.getImage() != null) {
				car.setImage(carDto.getImage().getBytes());
			}
			carRepository.save(car);
			return true;
		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public List<CarDto> getAllCars() {
		// TODO Auto-generated method stub
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCar(Long id) {
		// TODO Auto-generated method stub
		carRepository.deleteById(id);

	}

	// update APi
	@Override
	public CarDto getCarById(Long id) {
		Optional<Car> optionalCar = carRepository.findById(id);
		return optionalCar.map(Car::getCarDto).orElse(null);

	}

	@Override
//	public boolean updateCar(Long carId, CarDto carDto) throws IOException {
//		// TODO Auto-generated method stub
//		Optional<Car> optionalCar = carRepository.findById(carId);
//		if (optionalCar.isPresent()) {
//			Car existingCar = optionalCar.get();
//			if (carDto.getImage() != null) {
//				existingCar.setImage(carDto.getImage().getBytes());
//			}
//			existingCar.setPrice(carDto.getPrice());
//			existingCar.setYear(carDto.getYear());
//			existingCar.setType(carDto.getType());
//			existingCar.setDescription(carDto.getDescription());
//			existingCar.setTransmission(carDto.getTransmission());
//			existingCar.setColor(carDto.getColor());
//			existingCar.setName(carDto.getName());
//			existingCar.setBrand(carDto.getBrand());
//			carRepository.save(existingCar);
//			return true;
//
//		} else {
//
//			return false;
//		}
//
//	}
	public boolean updateCar(Long carId, CarDto carDto) throws IOException {
		Optional<Car> optionalCar = carRepository.findById(carId);

		if (optionalCar.isEmpty()) {
			return false; // Car not found
		}

		Car existingCar = optionalCar.get();

		// Update fields if they are not null
		if (carDto.getImage() != null) {
			try {
				existingCar.setImage(carDto.getImage().getBytes());
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		try {
//			if (carDto.getYear() != null) {
//				Date date = Constant.getDateFormat().parse(carDto.getYear()); // Parse the string to Date
//				existingCar.setYear(date);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		Optional.ofNullable(carDto.getPrice()).ifPresent(existingCar::setPrice);
		Optional.ofNullable(carDto.getYear()).ifPresent(existingCar::setYear);
		Optional.ofNullable(carDto.getType()).ifPresent(existingCar::setType);
		Optional.ofNullable(carDto.getDescription()).ifPresent(existingCar::setDescription);
		Optional.ofNullable(carDto.getTransmission()).ifPresent(existingCar::setTransmission);
		Optional.ofNullable(carDto.getColor()).ifPresent(existingCar::setColor);
		Optional.ofNullable(carDto.getName()).ifPresent(existingCar::setName);
		Optional.ofNullable(carDto.getBrand()).ifPresent(existingCar::setBrand);

		carRepository.save(existingCar);
		return true;
	}

	@Override
	public List<BookACarDto> getBookings() {
		// methods for get bookings
		return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
	}

//	public boolean changeBookingStatus(Long bookingId, String status) {
//		// for approve and reject booking
//		Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);
//		if (optionalBookACar.isPresent()) {
//			BookACar existingBookACar = optionalBookACar.get();
//			if (Objects.equals(status, "Approve")) {
//				existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
//			} else {
//				existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
//				bookACarRepository.save(existingBookACar);
//				return true;
//			}
//		}
//		return false;
//	}
	@Override
	public boolean changeBookingStatus(Long bookingId, String status) {
		Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);

		if (optionalBookACar.isPresent()) {
			BookACar existingBookACar = optionalBookACar.get();

			if ("Approve".equalsIgnoreCase(status)) {
				existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
			} else {
				existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
			}

			bookACarRepository.save(existingBookACar);
			return true;
		}

		return false;
	}

	@Override
	public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
		// TODO Auto-generated method
		Car car = new Car();
		car.setBrand(searchCarDto.getBrand());
		car.setType(searchCarDto.getType());
		car.setTransmission(searchCarDto.getTransmission());
		car.setColor(searchCarDto.getColor());
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> carExample = Example.of(car, exampleMatcher);
		List<Car> carList = carRepository.findAll(carExample);
		CarDtoListDto carDtoListDto = new CarDtoListDto();
		carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
		return carDtoListDto;
	}

}
