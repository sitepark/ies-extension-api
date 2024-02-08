package com.sitepark.ies.extension.api.exeptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.sitepark.ies.extension.api.Extension;
import org.junit.jupiter.api.Test;

class InvalidExtensionClassExceptionTest {

  @Test
  void testWithMessage() {
    Class<Extension> extensionClass = Extension.class;
    InvalidExtensionClassException e =
        assertThrows(
            InvalidExtensionClassException.class,
            () -> {
              throw new InvalidExtensionClassException(extensionClass, "message");
            },
            "throw InitializationFailedException expected");

    assertEquals(extensionClass, e.getExtensionClass(), "unexpected extensionClass");
    assertEquals("message", e.getMessage(), "unexpected message");
    assertNull(e.getCause(), "cause should be null");
  }

  @Test
  void testWithMessageAndThrowable() {
    Class<Extension> extensionClass = Extension.class;
    Throwable t = mock();
    InvalidExtensionClassException e =
        assertThrows(
            InvalidExtensionClassException.class,
            () -> {
              throw new InvalidExtensionClassException(extensionClass, "message", t);
            },
            "throw InitializationFailedException expected");

    assertEquals(extensionClass, e.getExtensionClass(), "unexpected extensionClass");
    assertEquals("message", e.getMessage(), "unexpected message");
    assertEquals(t, e.getCause(), "unexpected cause");
  }
}
