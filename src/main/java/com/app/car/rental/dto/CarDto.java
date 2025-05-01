package com.app.car.rental.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CarDto {
	private Long id;

	private String brand;

	private String color;

	private String name;

	private String transmission;

	private String description;

	private Long price;

	// @DateTimeFormat(pattern = "EEE MMM dd yyyy HH:mm:ss 'IST'Z (zzzz)")
	private Date year;

	private MultipartFile image;

	private byte[] returnedImage;

	private String type;

	public void setType(String type) {
		this.type = type;
	}

	// Getter method (if needed)
	public String getType() {
		return type;
	}

}
