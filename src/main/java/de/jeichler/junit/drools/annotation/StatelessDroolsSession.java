package de.jeichler.junit.drools.annotation;

import org.kie.api.runtime.StatelessKieSession;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to inject Drools {@link StatelessKieSession}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface StatelessDroolsSession {
}