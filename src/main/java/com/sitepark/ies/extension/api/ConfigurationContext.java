package com.sitepark.ies.extension.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Configures the objects of an extension. An extension receives it in its static method annotated
 * with {@link com.sitepark.ies.extension.api.annotations.ConfigureFactory}.
 *
 * <p>The configuration does not depend on a container. The IES creates the registered classes and
 * injects them through their {@code jakarta.inject.Inject} constructor; the classes of the IES and
 * of other extensions are available for injection.
 *
 * <pre>{@code
 * @ConfigureFactory
 * public static void configure(ConfigurationContext context) {
 *   context
 *       .bind(CounterRepository.class, MyBatisCounterRepository.class)
 *       .addMappers(MyBatisCounterRepositoryMapper.class);
 * }
 * }</pre>
 */
public final class ConfigurationContext {

  private final List<Class<?>> components = new ArrayList<>();

  private final List<Binding> bindings = new ArrayList<>();

  private final List<Class<?>> mappers = new ArrayList<>();

  private @Nullable ClassFinder classFinder;

  public ConfigurationContext() {}

  /**
   * Registers classes that are created by the IES, e.g. services the extension injects by their
   * class. The classes of the extension that the IES looks for itself (extensions, resolvers,
   * resources) need not be registered.
   *
   * @param components the classes to create
   * @return this context
   */
  public ConfigurationContext register(Class<?>... components) {
    for (Class<?> component : components) {
      this.components.add(Objects.requireNonNull(component, "component is null"));
    }
    return this;
  }

  /**
   * Binds a type to its implementation: an injection of the type gets an instance of the
   * implementation. A binding replaces a default binding ({@link #bindDefault}) of the type; two
   * bindings of the same type are an error.
   *
   * @param <T> the bound type
   * @param type the type that is injected, usually an interface
   * @param implementation the class the IES creates for it
   * @return this context
   */
  public <T> ConfigurationContext bind(Class<T> type, Class<? extends T> implementation) {
    this.bindings.add(new Binding(type, implementation, false));
    return this;
  }

  /**
   * Binds a type to an implementation that is used unless an extension binds the type ({@link
   * #bind}), e.g. a default that a project replaces.
   *
   * @param <T> the bound type
   * @param type the type that is injected, usually an interface
   * @param implementation the class the IES creates for it unless the type is bound otherwise
   * @return this context
   */
  public <T> ConfigurationContext bindDefault(Class<T> type, Class<? extends T> implementation) {
    this.bindings.add(new Binding(type, implementation, true));
    return this;
  }

  /**
   * Adds the MyBatis mapper interfaces of the extension. The IES creates them on the database of
   * the IES, they can be injected; the statements take part in the transactions of the IES. The
   * mappers are loaded through the class loader of the extension.
   *
   * @param mappers the mapper interfaces
   * @return this context
   */
  public ConfigurationContext addMappers(Class<?>... mappers) {
    for (Class<?> mapper : mappers) {
      Objects.requireNonNull(mapper, "mapper is null");
      if (!mapper.isInterface()) {
        throw new IllegalArgumentException(mapper.getName() + " is not an interface");
      }
      this.mappers.add(mapper);
    }
    return this;
  }

  /** The registered classes, in the order of their registration. */
  public List<Class<?>> getComponents() {
    return Collections.unmodifiableList(this.components);
  }

  /** The bindings and default bindings, in the order of their registration. */
  public List<Binding> getBindings() {
    return Collections.unmodifiableList(this.bindings);
  }

  /** The MyBatis mapper interfaces, in the order of their registration. */
  public List<Class<?>> getMappers() {
    return Collections.unmodifiableList(this.mappers);
  }

  public void setClassFinder(ClassFinder classFinder) {
    this.classFinder = classFinder;
  }

  public @Nullable ClassFinder getClassFinder() {
    return this.classFinder;
  }

  /**
   * A binding of a type to its implementation.
   *
   * @param type the type that is injected
   * @param implementation the class that is created for it
   * @param isDefault {@code true} for a default binding, which another binding of the type replaces
   */
  public record Binding(Class<?> type, Class<?> implementation, boolean isDefault) {

    public Binding {
      Objects.requireNonNull(type, "type is null");
      Objects.requireNonNull(implementation, "implementation is null");
      if (!type.isAssignableFrom(implementation)) {
        throw new IllegalArgumentException(
            implementation.getName() + " is not a " + type.getName());
      }
    }
  }
}
