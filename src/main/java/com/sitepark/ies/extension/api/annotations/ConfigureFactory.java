package com.sitepark.ies.extension.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Markiert eine Methode als EventHandler eines {@link EventBus}. Die Methoden werden über
 * {@link AnnotatedHandlerFinder} ermittelt.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConfigureFactory {
}
