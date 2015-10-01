package com.github.jeichler.junit.drools.session;

import org.drools.core.command.runtime.process.StartProcessCommand;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public final class DroolsStatelessSession implements DroolsSession<StatelessKieSession> {

    private final StatelessKieSession kSession;
    private final List<Command<?>> commands = new ArrayList<>();
    private int numberOfFiredRules;

    public DroolsStatelessSession(StatelessKieSession statelessKieSession) {
        this.kSession = statelessKieSession;
    }

    @Override
    public void fireAllRules() {
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        fireAllRulesCommand.setOutIdentifier(IDENTIFIER_NUMBER_OF_FIRED_RULES);
        commands.add(fireAllRulesCommand);
        ExecutionResults executionResults = kSession.execute(CommandFactory.newBatchExecution(commands));
        this.numberOfFiredRules = (Integer) executionResults.getValue(IDENTIFIER_NUMBER_OF_FIRED_RULES);
    }

    @Override
    public void startProcess(final String processId) {
        commands.add(new StartProcessCommand(processId));
        kSession.execute(CommandFactory.newBatchExecution(commands));
    }

    @Override
    public void insert(Object... objects) {
        if (objects != null) {
            for (Object object : objects) {
                if (object != null) {
                    commands.add(CommandFactory.newInsert(object));
                }
            }
        }
    }

    @Override
    public int getNumberOfFiredRules() {
        return this.numberOfFiredRules;
    }
}