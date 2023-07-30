package de.sboe0705.utils;

public class CustomMockMvcResultMatchers {

	public static ErrorMessageResultMatchers errorMessage() {
		return new ErrorMessageResultMatchers();
	}

}
