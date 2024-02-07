package com.sitepark.ies.extension.api;

import com.google.inject.Module;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;

public class ConfigurationContext {

  private final List<Module> injectionModuleList = new ArrayList<>();

  private ClassFinder classFinder;

  private final List<EventListener> servletContextEventListenerList = new ArrayList<>();

  public ConfigurationContext() {}

  public void addServletContextEventListener(EventListener listener) {
    this.servletContextEventListenerList.add(listener);
  }

  public List<EventListener> getServletContextEventListener() {
    return Collections.unmodifiableList(this.servletContextEventListenerList);
  }

  public void addInjectionModule(Module injectionModule) {
    this.injectionModuleList.add(injectionModule);
  }

  public List<Module> getInjectionModuleList() {
    return Collections.unmodifiableList(this.injectionModuleList);
  }

  public void setClassFinder(ClassFinder classFinder) {
    this.classFinder = classFinder;
  }

  public ClassFinder getClassFinder() {
    return this.classFinder;
  }
}
