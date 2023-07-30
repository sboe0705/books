package de.sboe0705.utils;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultMatcher;

public final class ErrorMessageResultMatchers {

	/**
	 * Protected constructor. Use
	 * {@link CustomMockMvcResultMatchers#errorMessage()}.
	 */
	protected ErrorMessageResultMatchers() {
	}

	public ResultMatcher is(Matcher<? super String> matcher) {
		return result -> assertThat("Error message", result.getResponse().getErrorMessage(), matcher);
	}

}