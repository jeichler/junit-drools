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
    session.insert(message);
    session.fireAllRules();
    Assert.assertTrue(session.getNumberOfFiredRules() == expectedNumberOfFiredRules);
  }

}
```
