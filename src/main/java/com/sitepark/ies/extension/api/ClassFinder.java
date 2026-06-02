package com.sitepark.ies.extension.api;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ClassFinder {

  <T> List<Class<? extends T>> findBySubType(Class<T> type);

  <A extends Annotation> List<Class<?>> findByAnnotation(Class<A> annotation);

  <T, A extends Annotation> List<Class<? extends T>> findByAnnotation(
      Class<T> type, Class<A> annotation);

  Class<?> loadClass(String className) throws ClassNotFoundException;

  void initialize();

  void reset();
}
