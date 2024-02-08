package com.sitepark.ies.extension.api.exeptions;

public abstract class ExtensionException extends RuntimeException {

  private static final long serialVersionUID = 3712489965941435766L;

  public ExtensionException(String message) {
    super(message);
  }

  public ExtensionException(String message, Throwable t) {
    super(message, t);
  }
}
