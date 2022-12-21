package com.sitepark.ies.extension.api.port;

public interface EventBusSubscriber {

	/**
	 * Subscribe for events that are posted on the EventBus.
	 *
	 * @param subscriber The object to subscribe.
	 */
	void register(Object subscriber);

	/**
	 * Unsubscribe from events that are posted on the EventBus.
	 *
	 * @param subscriber The object to unsubscribe.
	 */
	void unregister(Object subscriber);
}