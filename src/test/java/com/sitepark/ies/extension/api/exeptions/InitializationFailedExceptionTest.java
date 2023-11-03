package com.sitepark.ies.extension.api.exeptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class InitializationFailedExceptionTest {

	@Test
	void testWithMessage() {
		InitializationFailedException e = assertThrows(
				InitializationFailedException.class, () -> {
						throw new InitializationFailedException("message");
				}, "throw InitializationFailedException expected");
		assertEquals("message", e.getMessage(), "unexpected message");
		assertNull(e.getCause(), "cause should be null");
	}

	@Test
	void testWithMessageAndThrowable() {
		Throwable t = mock();
		InitializationFailedException e = assertThrows(
				InitializationFailedException.class, () -> {
						throw new InitializationFailedException("message", t);
				}, "throw InitializationFailedException expected");

		assertEquals("message", e.getMessage(), "unexpected message");
		assertEquals(t, e.getCause(), "unexpected cause");
	}
}
