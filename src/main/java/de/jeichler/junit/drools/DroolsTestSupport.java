package de.jeichler.junit.drools;

import org.junit.Assert;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class DroolsTestSupport {

  private final DroolsSession<?> droolsSession;
  private final KieBase kieBase;

  public DroolsTestSupport(String ruleFile, SessionType sessionType) {
    Assert.assertNotNull(ruleFile);
    this.kieBase = createKieContainer(ruleFile).getKieBase();
    this.droolsSession = SessionType.STATEFUL.equals(sessionType) ? createSessionSingleRuleFile() : createStatelessSessionSingleRuleFile();
  }

  private DroolsSession<?> createStatelessSessionSingleRuleFile() {
    StatelessKieSession statelessKieSession = kieBase.newStatelessKieSession();
    return new DroolsStatelessSession(statelessKieSession);
  }

  private DroolsSession<?> createSessionSingleRuleFile() {
    KieSession kSession = kieBase.newKieSession();
    return new DroolsStatefulSession(kSession);
  }

  private KieContainer createKieContainer(String ruleFile) {
    KieServices ks = KieServices.Factory.get();
    KieFileSystem kfs = ks.newKieFileSystem();
    InputStream ruleFileIs = DroolsTestSupport.class
        .getResourceAsStream("/" + ruleFile);

    Assert.assertNotNull("Can't open stream for rule file. Does it exist?", ruleFileIs);

    KieBuilder kieBuilder = ks.newKieBuilder(kfs);
    ReleaseId releaseId = KieServices.Factory.get().newReleaseId(
        "com.acme.dummy.package", "test", "1.0.0");

    kfs.generateAndWritePomXML(releaseId);
    kfs.write(ks.getResources().newInputStreamResource(ruleFileIs)
        .setSourcePath(ruleFile));

    try {
      ruleFileIs.close();
    } catch (IOException e) {
      throw new RuntimeException(
          "InputStream of rule file could not be closed.");
    }

    kieBuilder.buildAll();

    StringBuilder message = new StringBuilder();
    List<Message> messages = kieBuilder.getResults().getMessages();
    for (Message m : messages) {
      message.append(m.toString()).append("\n");
    }
    Assert.assertEquals(message.toString(), 0, messages.size());

    KieModule kieModule = kieBuilder.getKieModule();
    Assert.assertEquals(releaseId, kieModule.getReleaseId());

    return ks.newKieContainer(releaseId);
  }

  public DroolsSession getDroolsSession() {
    return this.droolsSession;
  }
}