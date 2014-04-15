package de.jeichler.test;

import de.jeichler.junit.drools.DroolsJUnitRunner;
import de.jeichler.junit.drools.DroolsSession;
import de.jeichler.junit.drools.annotation.DroolsFiles;
import de.jeichler.junit.drools.annotation.StatefulDroolsSession;
import de.jeichler.junit.drools.annotation.StatelessDroolsSession;
import de.jeichler.test.model.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@DroolsFiles(ruleFile = "HelloReceiver.drl")
@RunWith(DroolsJUnitRunner.class)
public class DroolsSessionTest {

  @StatefulDroolsSession
  private DroolsSession statefulSession;

  @StatelessDroolsSession
  private DroolsSession statelessSession;

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

  private void insertAndFire(DroolsSession session, String messageReceiver, int expectedNumberOfFiredRules) {
    final Message message = new Message(messageReceiver);
    session.insert(message);
    session.fireAllRules();
    Assert.assertTrue(session.getNumberOfFiredRules() == expectedNumberOfFiredRules);
  }

}
