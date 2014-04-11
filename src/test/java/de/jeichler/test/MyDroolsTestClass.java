package de.jeichler.test;

import de.jeichler.junit.drools.DroolsJUnitRunner;
import de.jeichler.junit.drools.DroolsSession;
import de.jeichler.junit.drools.DroolsStatelessSession;
import de.jeichler.junit.drools.annotation.DroolsFiles;
import de.jeichler.junit.drools.annotation.StatelessDroolsSession;
import org.junit.Test;
import org.junit.runner.RunWith;

@DroolsFiles(ruleFile = "Rule1.drl")
@RunWith(DroolsJUnitRunner.class)
public class MyDroolsTestClass {

  @StatelessDroolsSession
  private DroolsSession<DroolsStatelessSession> droolsSession;

  @Test
  public void test() {
    this.droolsSession.fireAllRules();
    System.out.print(droolsSession.getNumberOfFiredRules());
  }
}
