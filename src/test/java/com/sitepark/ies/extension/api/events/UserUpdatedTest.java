package com.sitepark.ies.extension.api.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class UserUpdatedTest {

	@Test
	void testSetId() {
		UserUpdated event = UserUpdated.builder()
				.id(123).build();
		assertEquals(123, event.getId(), "unexpected id");
	}

	@Test
	@SuppressFBWarnings("RV_EXCEPTION_NOT_THROWN")
	void testInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> {
			UserUpdated.builder().id(0);
		});
	}

}
