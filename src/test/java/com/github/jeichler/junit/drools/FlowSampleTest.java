package com.github.jeichler.junit.drools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.jeichler.junit.drools.annotation.DroolsFiles;
import com.github.jeichler.junit.drools.annotation.StatefulDroolsSession;
import com.github.jeichler.junit.drools.annotation.StatelessDroolsSession;
import com.github.jeichler.junit.drools.model.Message;
import com.github.jeichler.junit.drools.session.DroolsSession;

@DroolsFiles(ruleFiles = {"flow.bpmn", "HelloReceiverFlow.drl"})
@RunWith(DroolsJUnitRunner.class)
public class FlowSampleTest {

    @StatefulDroolsSession
    private DroolsSession<?> statefulSession;

    @StatelessDroolsSession
    private DroolsSession<?> statelessSession;

    @Test
    public void stateful_bobMustBeGreeted() {
        Message message = insertAndFire(statefulSession, "Bob");
        assertEquals("Hello Bob", message.getContent());
    }

    @Test
    public void stateless_bobMustBeGreeted() {
        Message message = insertAndFire(statelessSession, "Bob");
        assertEquals("Hello Bob", message.getContent());
    }

    @Test
    public void stateful_aliceMustNotBeGreeted() {
        Message message = insertAndFire(statefulSession, "Alice");
        assertNull(message.getContent());
    }

    @Test
    public void stateless_aliceMustNotBeGreeted() {
        Message message = insertAndFire(statelessSession, "Alice");
        assertNull(message.getContent());
    }

    private Message insertAndFire(DroolsSession<?> session, String messageReceiver) {
        final Message message = new Message(messageReceiver);
        session.insert(message);
        session.startProcess("Sample");
        return message;

    }
}