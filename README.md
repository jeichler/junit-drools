[![Build Status](https://travis-ci.org/jeichler/junit-drools.svg?branch=master)](https://travis-ci.org/jeichler/junit-drools)


Usage
=============
```java
@DroolsFiles(ruleFiles = {"some/folder/in/resources/YourRuleFile.drl", "some/folder/in/resources/YourRuleFlow.bpmn"})
@RunWith(DroolsJUnitRunner.class)
public class TestClass {

  //@StatelessDroolsSession
  @StatefulDroolsSession
  private DroolsSession session;
  
  //...
  
  @Test
  public void testMethod() {
    session.insert(yourObject);
    session.fireAllRules();
    Assert.assertTrue(session.getNumberOfFiredRules() == expectedNumberOfFiredRules);
  }

}
```

Some thoughts about the usage
===============

Though I added support for rule flows (a pretty basic one, and yes, on purpose, i skipped adding whole folders at once because I'm not convinced that it helps testing your application.) I'm not a big fan of seeing them in unit tests). They should be part of integration tests and hence be run in the respective application context and the flows should be started by the respective components in your application. However for a quick peek you might use this runner for flows as well.
