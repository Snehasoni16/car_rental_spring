package com.app.car.rental.dto;

import org.springframework.web.multipart.MultipartFile;

public class GetCarDTO {

	private Long id;

	private String brand;

	private String color;

	private String name;

	private String transmission;

	private String description;

	private Long price;

	private String year;

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
