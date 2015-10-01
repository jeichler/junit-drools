package com.github.jeichler.junit.drools;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.jeichler.junit.drools.annotation.DroolsFiles;
import com.github.jeichler.junit.drools.annotation.StatefulDroolsSession;
import com.github.jeichler.junit.drools.annotation.StatelessDroolsSession;
import com.github.jeichler.junit.drools.model.Message;
import com.github.jeichler.junit.drools.session.DroolsSession;

@DroolsFiles(ruleFiles = "HelloReceiver.drl")
@RunWith(DroolsJUnitRunner.class)
public class SingleRuleFileSampleTest {

    @StatefulDroolsSession
    private DroolsSession<?> statefulSession;

    @StatelessDroolsSession
    private DroolsSession<?> statelessSession;

    @Test
    public void stateful_bobMustBeGreeted() {
        insertAndFire(statefulSession, "Bob", 1);
    }

    @Test
    public void stateless_bobMustBeGreeted() {
        insertAndFire(statelessSession, "Bob", 1);
    }

    @Test
    public void stateful_aliceMustNotBeGreeted() {
        insertAndFire(statefulSession, "Alice", 0);
    }

    @Test
    public void stateless_aliceMustNotBeGreeted() {
        insertAndFire(statelessSession, "Alice", 0);
    }

    private void insertAndFire(DroolsSession<?> session, String messageReceiver, int expectedNumberOfFiredRules) {
        final Message message = new Message(messageReceiver);
        session.insert(message);
        session.fireAllRules();
        Assert.assertTrue(session.getNumberOfFiredRules() == expectedNumberOfFiredRules);
    }
}