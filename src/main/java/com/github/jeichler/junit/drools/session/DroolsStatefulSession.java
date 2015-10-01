package com.github.jeichler.junit.drools.session;

import org.kie.api.runtime.KieSession;

public final class DroolsStatefulSession implements DroolsSession<KieSession> {

    private final KieSession statefulSession;

    private int numberOfFiredRules;

    public DroolsStatefulSession(final KieSession kSession) {
        this.statefulSession = kSession;
    }

    @Override
    public void fireAllRules() {
        this.numberOfFiredRules = this.statefulSession.fireAllRules();
    }

    @Override
    public void startProcess(final String processId) {
        this.statefulSession.startProcess(processId);
    }

    @Override
    public void insert(final Object... objects) {
        this.statefulSession.insert(objects);
    }

    @Override
    public int getNumberOfFiredRules() {
        return this.numberOfFiredRules;
    }
}