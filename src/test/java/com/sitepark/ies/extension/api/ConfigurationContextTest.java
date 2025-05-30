package com.sitepark.ies.extension.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.google.inject.Module;
import java.util.Collections;
import java.util.EventListener;
import org.junit.jupiter.api.Test;

class ConfigurationContextTest {

  @Test
  void testAddServletContextEventListener() {

    EventListener listener = mock();
    ConfigurationContext ctx = new ConfigurationContext();
    ctx.addServletContextEventListener(listener);

    assertEquals(
        Collections.singletonList(listener),
        ctx.getServletContextEventListener(),
        "listener expected");
  }

  @Test
  void testAddInjectionModule() {

    Module injectionModule = mock();
    ConfigurationContext ctx = new ConfigurationContext();
    ctx.addInjectionModule(injectionModule);

    assertEquals(
        Collections.singletonList(injectionModule),
        ctx.getInjectionModuleList(),
        "injectionModule expected");
  }

  @Test
  void testSetClassFinder() {

    ClassFinder classFinder = mock();
    ConfigurationContext ctx = new ConfigurationContext();
    ctx.setClassFinder(classFinder);

    assertEquals(classFinder, ctx.getClassFinder(), "classFinder expected");
  }
}
