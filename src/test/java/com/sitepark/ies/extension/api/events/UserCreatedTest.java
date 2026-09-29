package com.sitepark.ies.extension.api.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserCreatedTest {

  @Test
  void testSetId() {
    UserCreated event = UserCreated.builder().id(123).build();
    assertEquals(123, event.getId(), "unexpected id");
  }

  @Test
  void testInvalidId() {
    assertThrows(IllegalArgumentException.class, () -> UserCreated.builder().id(0));
  }
}
