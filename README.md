# How To Use

IES extensions extend the functionality of the IES. With the help of an API these extensions can be implemented.

Add the following Maven dependency to use the API.

```xml
<dependency>
	<groupId>com.sitepark.ies</groupId>
	<artifactId>ies-extension-api</artifactId>
	<version>1.0</version>
	<scope>provided</scope>
</dependency>
```


The starting point of each extension is the interface `com.sitepark.ies.extension.api.Extension`.

```java
package com.sitepark.ies.extension.api;

public interface Extension {
	void initialize();
	default void destroy() { }
}
```

The `initialize()` method is called when the extension is started. Here e.g. event handlers can be registered or background processes can be started.

The `destroy()` method is called when the extension is terminated. If event handlers have been registered, they must be unregistered here. Background processes must also be stopped here.

All extensions that implement this interface will be executed automatically. It is not necessary to register them anywhere.

## Use dependency-injection

There are several services that can be used via the API. These are provided via port interfaces. You can access these services via dependency injection. All port interfaces that can be used via the API are contained in the package `com.sitepark.ies.extension.api.port`.


For this purpose the constructor must be marked with the annotation `@javax.inject.Inject`.

```java
import javax.inject.Inject;
import com.sitepark.ies.extension.api.Extension;

public class MyExtension implements Extension {

	private final SomeService someService;

	@Inject
	protected MyExtension(SomeService someService) {
		this.someService = someService;
	}
}
```

Maven dependency for `@Inject`

```xml
<dependency>
	<groupId>javax.inject</groupId>
	<artifactId>javax.inject</artifactId>
	<version>1</version>
	<scope>provided</scope>
</dependency>
```

## Register event handler

Extensions can react to events in the IES. All events to which the extension can react are contained in the package `com.sitepark.ies.extension.api.events`.

To register a handler for a specific event the port interface `EventBusSubscriber` is used. An object is passed to the `register()` method. This is scanned for the `@com.sitepark.ies.extension.api.annotations.Subscribe` annotation and registers the appropriately marked methods for the event that the method accepts. Inheritance hierarchies of the event classes are supported.

It is important that registered handlers are unregistered via the `unregister()` method of the `EventBusSubscriber` when the extension is terminated.

```java
import javax.inject.Inject;
import com.sitepark.ies.extension.api.Extension;
import com.sitepark.ies.extension.api.annotations.Subscribe;
import com.sitepark.ies.extension.api.events.ContentEntryPurged;
import com.sitepark.ies.extension.api.port.EventBusSubscriber;

public class MyExtension implements Extension {

	private EventBusSubscriber eventBusSubscriber;

	@Inject
	protected MyExtension(EventBusSubscriber eventBusSubscriber) {
		this.eventBusSubscriber = eventBusSubscriber;
	}

	@Override
	public void initialize() {
		this.eventBusSubscriber.register(this);
	}

	@Override
	public void destroy() {
		this.eventBusSubscriber.unregister(this);
	}

   	@Subscribe
	public void purgeHandler(ContentEntryPurged purgeEvent) {
		// todo something
	}
}
```


## Inject your own classes

The extension class itself serves only as an entry point. In most cases it makes sense to outsource the logic of the extension into different classes. These classes should also be injected into the extension constructor.

The own classes can also be injected in the extension constructor. Also the own classes can be annotated with `@Inject` to inject own or API classes.

```java
import javax.inject.Inject;
import com.sitepark.ies.extension.api.annotations.Subscribe;
import com.sitepark.ies.extension.api.events.ContentEntryPurged;
import com.sitepark.ies.extension.api.port.EventBusSubscriber;

public class MyService {

	private EventBusSubscriber eventBusSubscriber;

	@Inject
	protected MyService(EventBusSubscriber eventBusSubscriber) {
		this.eventBusSubscriber = eventBusSubscriber;
	}

	@Override
	public void initialize() {
		this.eventBusSubscriber.register(this);
	}

	@Override
	public void destroy() {
		this.eventBusSubscriber.unregister(this);
	}

   	@Subscribe
	public void purgeHandler(ContentEntryPurged purgeEvent) {
		// todo something
	}
}
```
Use `MyService` in `MyExtension`

```java
import javax.inject.Inject;
import com.sitepark.ies.extension.api.Extension;

public class MyExtension implements Extension {

	private MyService myService;

	@Inject
	protected MyExtension(MyService myService) {
		this.myService = myService;
	}

	@Override
	public void initialize() {
		this.myService.initialize();
	}

	@Override
	public void destroy() {
		this.myService.destroy();
	}
}
```

## Advanced dependency-injection

The IES uses [Guice](https://github.com/google/guice/wiki) as the dependency injection framework.

Maven dependency:

```xml
<dependency>
	<groupId>com.google.inject</groupId>
	<artifactId>guice</artifactId>
	<version>5.1.0</version>
	<scope>provided</scope>
</dependency>
```

The full functionality of the framework can also be used in the extension. For this purpose it is necessary to create a [Guice module](https://github.com/google/guice/wiki/GettingStarted#guice-modules). This module must be known before the extension object is created. Therefore a static method is used here, with which Factory configurations can be set. The extensions are created via a factory, whose behavior can be controlled via the factory configuration.

To set the created Guice module in the factory configuration, a static method is marked in the extension class with the annotation `@com.sitepark.ies.extension.api.annotations.ConfigureFactory`. The marked method must take an argument of the `com.sitepark.ies.extension.api.FactoryConfig` class. The `FactoryConfig` can be used to pass the Guice module using the `addInjectionModule()` method.

```java
import com.google.inject.AbstractModule;

public class MyInjectionModule extends AbstractModule {
	@Override
	protected void initialize() {
		// ...
	}
}
```

Set `MyInjectionModule` to `FactoryConfig`

```java
import com.sitepark.ies.extension.api.FactoryConfig;
import com.sitepark.ies.extension.api.Extension;
import com.sitepark.ies.extension.api.annotations.ConfigureFactory;

public class MyExtension implements Extension {

	@ConfigureFactory
	public static void configureInjection(FactoryConfig config) {
		config.addInjectionModule(new MyInjectionModule());
	}
}
```

`@ConfigureFactory` can only be used in classes that implement the `Extension` interface.

## Database operations

The IES uses [MyBatis](https://mybatis.org/mybatis-3/), a SQL mapping framework. This can also be used in the extensions.

MyBatis has a [Guice integration](http://mybatis.org/guice/getting-started.html) that can also be used for IES extensions.

Maven dependency:

```xml
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.5.9</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis-guice</artifactId>
	<version>3.17</version>
	<scope>provided</scope>
</dependency>
```


For this purpose `MyBatisModule` is extended as an injection module and made known to the extension factory.

```java
import org.mybatis.guice.MyBatisModule;

public class MyBatisInjectionModule extends MyBatisModule {
	@Override
	protected void initialize() {

		// ...

		addMapperClass(MyRepositoryMapper.class);
	}
}
```

Mapper example:
```java
import org.apache.ibatis.annotations.Delete;

public interface MyRepositoryMapper {

	@Delete("DELETE FROM `MyTable` where `id` = #{id}")
	public int delete(long id);
}
```

Set `MyBatisInjectionModule` to `FactoryConfig`

```java
import com.sitepark.ies.extension.api.FactoryConfig;
import com.sitepark.ies.extension.api.Extension;
import com.sitepark.ies.extension.api.annotations.ConfigureFactory;

public class MyExtension implements Extension {

	@ConfigureFactory
	public static void configureInjection(FactoryConfig config) {
		config.addInjectionModule(new MyBatisInjectionModule());
	}
}
```

`MyMapper` can now be injected.

```java
import javax.inject.Inject;

public class MyRepository {

	private final MyMapper mapper;

	@Inject
	protected MyRepository(MyMapper mapper) {
		this.mapper = mapper;
	}

	public void delete(long id) {
		this.mapper.delete(id);
	}
}
```

## Servlet support

Extensions can provide their own servlets. For this purpose, the IES uses the [Guice Servlet Extensions](https://github.com/google/guice/wiki/Servlets).

Maven dependencies:

```xml
	<dependency>
		<groupId>com.google.inject.extensions</groupId>
		<artifactId>guice-servlet</artifactId>
		<version>5.1.0</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
		<version>4.0.1</version>
		<scope>provided</scope>
	</dependency>
```

The extension provides the servlet. The extension itself does not have to be a webapplication.

```java
import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class MyServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().print("my-servlet");
	}
}
```

Dependency-injection can also be used here. See also [here](https://github.com/google/guice/wiki/ServletModule#available-injections) for more information.


With the `ServletModule` the servlets are configured.

```java
import com.google.inject.servlet.ServletModule;

public class MyServletInjectionModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/my").with(MyServlet.class);
	}
}
```

Now only the Injection module for the Factory has to be added.

Set `MyServletInjectionModule` to `FactoryConfig`

```java
import com.sitepark.ies.extension.api.FactoryConfig;
import com.sitepark.ies.extension.api.Extension;
import com.sitepark.ies.extension.api.annotations.ConfigureFactory;

public class MyExtension implements Extension {

	@ConfigureFactory
	public static void configureInjection(FactoryConfig config) {
		config.addInjectionModule(new MyServletInjectionModule());
	}
}
```

The servlet can then be accessed at the URL https://ies.domain.com/ies3/my.

To keep access to the `ServletRequest` and the `ServletContext`, they can be easily injected.




## GraphQL support

The IES provides a GraphQL endpoint. This is itself also implemented as an extension and can be extended via extensions.

Extensions can extend the schema and add resolvers for this endpoint. This functionality is provided by the extension `ies-graphql-extension` and can be used by other extensions.

To learn how to extend the GraphQL schema please read [here](https://github.com/sitepark/ies-graphql-extension-api#how-to-extend).


# Examples

There is a GitHub project with examples.

https://github.com/sitepark/ies-extension-examples
