package com.app.car.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.car.rental.dto.CarDto;
import com.app.car.rental.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	void save(CarDto existingCar);

	// private final CarRepository carRepository;
}
