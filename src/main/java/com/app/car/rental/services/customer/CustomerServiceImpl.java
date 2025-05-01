package com.app.car.rental.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
import com.app.car.rental.entity.User;
import com.app.car.rental.repository.BookACarRepository;
import com.app.car.rental.repository.CarRepository;
import com.app.car.rental.repository.UserRepository;
import com.app.car.rental.util.BookCarStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CustomerServiceImpl implements CustomerService {

	private final CarRepository carRepository;

	private final UserRepository userRepository;

	private final BookACarRepository bookACarRepository;

	@Override
	public List<CarDto> getAllCars() {

		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public boolean bookACar(BookACarDto bookACarDto) {

//		Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCardId());
//		Optional<User> optionalUser = userRepository;

		Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
		Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());

		if (optionalCar.isPresent() && optionalUser.isPresent()) {
			Car existingCar = optionalCar.get();
			BookACar bookACar = new BookACar();
			bookACar.setUser(optionalUser.get());
			bookACar.setCar(existingCar);
			bookACar.setBookCarStatus(BookCarStatus.PENDING);
			bookACar.setFromDate(bookACarDto.getFromDate());
			bookACar.setToDate(bookACarDto.getToDate());
			long diffInMilliseconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
			long days = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);
			bookACar.setDays(days);
			bookACar.setPrice(existingCar.getPrice() * days);
			bookACarRepository.save(bookACar);

			return true;

		}
		return false;
	}

	@Override
	public CarDto getCarById(Long carId) {
		Optional<Car> optionalCar = carRepository.findById(carId);

		return optionalCar.map(Car::getCarDto).orElse(null);
	}

//	public List<BookACarDto> getBookingsByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto)
//				.collect(Collectors.toList());
//	}
	@Override
	public List<BookACarDto> getBookingsByUserId(Long userId) {
		return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto)
				.collect(Collectors.toList());
	}

	@Override
	public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
		// TODO Auto-generated method stub
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
