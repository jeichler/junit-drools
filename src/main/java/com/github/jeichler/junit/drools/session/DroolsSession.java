package com.github.jeichler.junit.drools.session;

public interface DroolsSession<T> {

    static final String IDENTIFIER_NUMBER_OF_FIRED_RULES = "numberOfFiredRules";

    void fireAllRules();

    void startProcess(String processId);

    void insert(Object... objects);

    int getNumberOfFiredRules();
}