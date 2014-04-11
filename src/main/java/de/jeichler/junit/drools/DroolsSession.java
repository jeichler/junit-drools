package de.jeichler.junit.drools;

public interface DroolsSession<T> {
  void fireAllRules();

  void insert(Object... objects);

  int getNumberOfFiredRules();

  T getSession();
}