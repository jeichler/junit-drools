package de.jeichler.junit.drools;

import de.jeichler.junit.drools.annotation.DroolsFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DroolsInjector {
  private static final Logger LOGGER = LoggerFactory.getLogger(DroolsInjector.class);

  public void initDrools(Object testClass) {
    if (testClass == null) {
      throw new IllegalArgumentException("Test class cannot be null");
    }

    LOGGER.info("Initializing Drools objects for test class: {}", testClass.getClass());

    DroolsAnnotationProcessor annotationProcessor = new DroolsAnnotationProcessor(testClass);
    DroolsFiles droolsFiles = annotationProcessor.getDroolsFiles();

    DroolsTestSupport testSupport = new DroolsTestSupport(droolsFiles.ruleFile(), getSessionType(annotationProcessor));
    annotationProcessor.setDroolsSession(testSupport.getDroolsSession());
  }

  private SessionType getSessionType(DroolsAnnotationProcessor droolsAnnotationProcessor) {
    if (droolsAnnotationProcessor.isStatefulDroolsAnnotationSet()) {
      return SessionType.STATEFUL;
    }
    return SessionType.STATELESS;
  }
}