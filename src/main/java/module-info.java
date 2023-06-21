module com.sitepark.ies.extension.api {
	exports com.sitepark.ies.extension.api;
	exports com.sitepark.ies.extension.api.exeptions;
	exports com.sitepark.ies.extension.api.events;
	exports com.sitepark.ies.extension.api.annotations;
	exports com.sitepark.ies.extension.api.port;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.google.guice;
}
