package com.github.jeichler.junit.drools;

import com.github.jeichler.junit.drools.annotation.DroolsFiles;
import com.github.jeichler.junit.drools.session.SessionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DroolsInjector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsInjector.class);

    public void initDrools(final Object testClass) {
        if (testClass == null) {
            throw new IllegalArgumentException("Test class must not be null");
        }

        LOGGER.debug("Initializing Drools objects for test class: {}", testClass.getClass());

        final DroolsAnnotationProcessor annotationProcessor = new DroolsAnnotationProcessor(testClass);
        final Class<?> annotation = annotationProcessor.getAnnotationType();

        if (annotation == DroolsFiles.class) {
            final DroolsFiles droolsFiles = testClass.getClass().getAnnotation(DroolsFiles.class);
            final DroolsTestSupport testSupport = new DroolsTestSupport(droolsFiles.ruleFiles(), getSessionType(annotationProcessor));
            annotationProcessor.setDroolsSession(testSupport.getDroolsSession());
        }
    }

    private SessionType getSessionType(DroolsAnnotationProcessor droolsAnnotationProcessor) {
        if (droolsAnnotationProcessor.isStatefulDroolsAnnotationSet()) {
            return SessionType.STATEFUL;
        }
        return SessionType.STATELESS;
    }
}