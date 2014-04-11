package de.jeichler.junit.drools;

import org.kie.api.runtime.KieSession;

public final class DroolsStatefulSession implements DroolsSession<KieSession> {

  private final KieSession statefulSession;

  private int numberOfFiredRules;

  public DroolsStatefulSession(KieSession kSession) {
    this.statefulSession = kSession;
  }

  @Override
  public void fireAllRules() {
    this.numberOfFiredRules = this.statefulSession.fireAllRules();
  }

  @Override
  public void insert(Object... objects) {
    this.statefulSession.insert(objects);
  }

  @Override
  public KieSession getSession() {
    return statefulSession;
  }

  @Override
  public int getNumberOfFiredRules() {
    return this.numberOfFiredRules;
  }
}