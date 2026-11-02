package com.sitepark.ies.extension.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.sitepark.ies.extension.api.ConfigurationContext.Binding;
import org.junit.jupiter.api.Test;

class ConfigurationContextTest {

  interface Repository {}

  static class MyBatisRepository implements Repository {}

  static class DefaultRepository implements Repository {}

  interface Mapper {}

  static class Service {}

  @Test
  void registersComponents() {
    ConfigurationContext context = new ConfigurationContext().register(Service.class);

    assertThat(context.getComponents()).containsExactly(Service.class);
  }

  @Test
  void bindsATypeToItsImplementation() {
    ConfigurationContext context =
        new ConfigurationContext().bind(Repository.class, MyBatisRepository.class);

    assertThat(context.getBindings())
        .containsExactly(new Binding(Repository.class, MyBatisRepository.class, false));
  }

  @Test
  void bindsADefault() {
    ConfigurationContext context =
        new ConfigurationContext().bindDefault(Repository.class, DefaultRepository.class);

    assertThat(context.getBindings())
        .containsExactly(new Binding(Repository.class, DefaultRepository.class, true));
  }

  @Test
  void keepsTheOrderOfTheBindings() {
    ConfigurationContext context =
        new ConfigurationContext()
            .bindDefault(Repository.class, DefaultRepository.class)
            .bind(Repository.class, MyBatisRepository.class);

    assertThat(context.getBindings())
        .extracting(Binding::implementation)
        .containsExactly(DefaultRepository.class, MyBatisRepository.class);
  }

  @Test
  void rejectsAnImplementationOfAnotherType() {
    assertThatThrownBy(() -> new Binding(Repository.class, Service.class, false))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addsMappers() {
    ConfigurationContext context = new ConfigurationContext().addMappers(Mapper.class);

    assertThat(context.getMappers()).containsExactly(Mapper.class);
  }

  @Test
  void rejectsAMapperThatIsNoInterface() {
    ConfigurationContext context = new ConfigurationContext();

    assertThatThrownBy(() -> context.addMappers(Service.class))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void returnsUnmodifiableLists() {
    ConfigurationContext context = new ConfigurationContext();

    assertThatThrownBy(() -> context.getComponents().add(Service.class))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void setsTheClassFinder() {
    ClassFinder classFinder = mock();
    ConfigurationContext context = new ConfigurationContext();
    context.setClassFinder(classFinder);

    assertThat(context.getClassFinder()).isSameAs(classFinder);
  }
}
