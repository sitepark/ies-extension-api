package com.sitepark.ies.extension.api.exeptions;

import com.sitepark.ies.extension.api.Extension;
import java.io.Serial;

public class InvalidExtensionClassException extends ExtensionException {

  @Serial private static final long serialVersionUID = 6670400793703514354L;

  private final Class<? extends Extension> extensionClass;

  public InvalidExtensionClassException(Class<? extends Extension> extensionClass, String message) {
    super(message);
    this.extensionClass = extensionClass;
  }

  public InvalidExtensionClassException(
      Class<? extends Extension> extensionClass, String message, Throwable t) {
    super(message, t);
    this.extensionClass = extensionClass;
  }

  public Class<? extends Extension> getExtensionClass() {
    return this.extensionClass;
  }
}
