package com.app.car.rental.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public interface Constant {

	public static SimpleDateFormat getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'IST'Z (zzzz)");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set to IST
		return sdf;
	}

}
