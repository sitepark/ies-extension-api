package com.sitepark.ies.extension.api.port;

public interface EventBusPublisher {

  /**
   * Posts the given event to the EventBus.
   *
   * @param event An event object.
   */
  void post(Object event);
}
