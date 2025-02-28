package com.sitepark.ies.extension.api;

/**
 * Starting point of each extension. The {@link #initialize()} method is called when the extension
 * is started. Here e.g. event handlers can be registered or Background processes. The {@link
 * #destroy()} method is called when the extension is terminated. If event handlers have been
 * registered, they must be unregistered here. Background processes must also be stopped here.
 */
public interface Extension {

  /**
   * Initialize the Extension. - event handlers can be registered - start Background processes - ...
   */
  void initialize();

  /**
   * Can contain cleanup code
   *
   * <p>- unregistered event handlers - stop Background processes - ...
   */
  default void destroy() {
    // there is nothing to do in the standard case
  }
}
