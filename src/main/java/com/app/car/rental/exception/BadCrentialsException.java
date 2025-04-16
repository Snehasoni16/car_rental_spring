package com.app.car.rental.exception;

public class BadCrentialsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageString;

	public BadCrentialsException(String messageString) {
		super();
		this.messageString = messageString;
	}

}
