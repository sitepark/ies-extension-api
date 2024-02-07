package com.sitepark.ies.extension.api;

import java.util.List;

public interface ClassFinder {

  <T> List<Class<? extends T>> findBySubType(Class<T> type);

  void initialize();

  public void reset();
}
