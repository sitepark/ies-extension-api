package com.sitepark.ies.extension.api;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class ExtensionTest {

	@Test
	void testDefaultDestroyMethod() {
		Extension extension = spy(Extension.class);
		extension.destroy();
		verify(extension).destroy();
	}
}
