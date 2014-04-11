package de.jeichler.junit.drools.annotation;

import org.kie.api.runtime.KieSession;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to inject Drools {@link KieSession}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface StatefulDroolsSession {

}