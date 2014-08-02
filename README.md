[![Build Status](https://travis-ci.org/jeichler/junit-drools.svg?branch=master)](https://travis-ci.org/jeichler/junit-drools)


Usage
=============
```java
@DroolsFiles(ruleFile = "some/folder/in/resources/YourRuleFile.drl")
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

Limitations
====================
Please note that a single rule file can be tested only. In case you need multiple files, you should take integration tests into consideration. This project is just a little helper to verify a single rule file does work as expected.
