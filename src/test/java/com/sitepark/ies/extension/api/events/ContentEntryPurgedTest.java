package com.sitepark.ies.extension.api.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ContentEntryPurgedTest {

  @Test
  void testSetId() {
    ContentEntryPurged event = ContentEntryPurged.builder().id(123).build();
    assertEquals(123, event.getId(), "unexpected id");
  }

  @Test
  void testInvalidId() {
    assertThrows(IllegalArgumentException.class, () -> ContentEntryPurged.builder().id(0));
  }
}
