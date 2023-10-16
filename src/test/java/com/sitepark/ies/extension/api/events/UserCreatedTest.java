package com.sitepark.ies.extension.api.events;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class UserCreatedTest {

	@Test
	@SuppressFBWarnings("RV_EXCEPTION_NOT_THROWN")
	void testInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> {
			UserCreated.builder().id(0);
		});
	}

}
