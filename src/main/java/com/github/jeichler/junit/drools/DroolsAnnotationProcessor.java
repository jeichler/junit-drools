package com.github.jeichler.junit.drools;

import com.github.jeichler.junit.drools.annotation.DroolsFiles;
import com.github.jeichler.junit.drools.annotation.StatefulDroolsSession;
import com.github.jeichler.junit.drools.annotation.StatelessDroolsSession;
import com.github.jeichler.junit.drools.session.DroolsSession;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

final class DroolsAnnotationProcessor {

    private final Object testClass;

    private boolean isStatefulSession;

    public DroolsAnnotationProcessor(Object testClass) {
        this.testClass = testClass;
    }

    public Class<?> getAnnotationType() {
        Annotation[] annotations = testClass.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            final String clazzname = annotation.annotationType().getCanonicalName();
            if (clazzname.equals(DroolsFiles.class.getCanonicalName())) {
                return DroolsFiles.class;
            }
        }
        throw new RuntimeException("seems you have missed to annotate your class properly");
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