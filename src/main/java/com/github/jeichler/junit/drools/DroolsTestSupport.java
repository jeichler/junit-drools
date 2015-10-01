package com.github.jeichler.junit.drools;

import com.github.jeichler.junit.drools.session.DroolsSession;
import com.github.jeichler.junit.drools.session.DroolsStatefulSession;
import com.github.jeichler.junit.drools.session.DroolsStatelessSession;
import com.github.jeichler.junit.drools.session.SessionType;
import org.junit.Assert;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class DroolsTestSupport {

    private final DroolsSession<?> droolsSession;

    private final KieBase kieBase;

    public DroolsTestSupport(String[] ruleFiles, SessionType sessionType) {
        Assert.assertNotNull(ruleFiles);
        this.kieBase = createKieContainer(ruleFiles).getKieBase();
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

    private KieContainer createKieContainer(String... ruleFiles) {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);

        for (String ruleFile : ruleFiles) {
            InputStream ruleFileIs = DroolsTestSupport.class.getResourceAsStream("/" + ruleFile);
            Assert.assertNotNull("Can't open stream for rule file. Does it exist?", ruleFileIs);
            kfs.write(ks.getResources().newInputStreamResource(ruleFileIs).setSourcePath(ruleFile));
            try {
                ruleFileIs.close();
            } catch (IOException e) {
                throw new RuntimeException("InputStream of rule file could not be closed.");
            }
        }
        kieBuilder.buildAll();

        StringBuilder message = new StringBuilder();
        List<Message> messages = kieBuilder.getResults().getMessages();
        for (Message m : messages) {
            message.append(m.toString()).append("\n");
        }
        Assert.assertEquals(message.toString(), 0, messages.size());
        return ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
    }

    public DroolsSession<?> getDroolsSession() {
        return this.droolsSession;
    }
}