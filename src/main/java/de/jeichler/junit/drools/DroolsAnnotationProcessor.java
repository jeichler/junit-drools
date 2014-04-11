package de.jeichler.junit.drools;

import de.jeichler.junit.drools.annotation.DroolsFiles;
import de.jeichler.junit.drools.annotation.StatefulDroolsSession;
import de.jeichler.junit.drools.annotation.StatelessDroolsSession;

import java.lang.reflect.Field;

final class DroolsAnnotationProcessor {

  private final Object testClass;

  private boolean isStatefulSession;

  public DroolsAnnotationProcessor(Object testClass) {
    this.testClass = testClass;
  }

  public DroolsFiles getDroolsFiles() {
    DroolsFiles droolsFiles = testClass.getClass().getAnnotation(DroolsFiles.class);

    if (droolsFiles == null) {
      throw new IllegalStateException("DroolsFiles annotation not set");
    }

    return droolsFiles;
  }

  public void setDroolsSession(DroolsSession<?> droolsSession) {
    for (Field field : testClass.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      isStatefulSession = field.isAnnotationPresent(StatefulDroolsSession.class);
      if (isStatefulSession || field.isAnnotationPresent(StatelessDroolsSession.class)) {
        try {
          field.set(testClass, droolsSession);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }

  }

  public boolean isStatefulDroolsAnnotationSet() {
    return this.isStatefulSession;
  }
}