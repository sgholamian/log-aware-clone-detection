//,temp,PaloAltoResource.java,590,618,temp,PaloAltoResource.java,552,579
//,2
public class xxx {
    private Answer execute(SetStaticNatRulesCommand cmd, int numRetries) {
        StaticNatRuleTO[] rules = cmd.getRules();

        try {
            ArrayList<IPaloAltoCommand> commandList = new ArrayList<IPaloAltoCommand>();

            for (StaticNatRuleTO rule : rules) {
                if (!rule.revoked()) {
                    manageStcNatRule(commandList, PaloAltoPrimative.ADD, rule);
                } else {
                    manageStcNatRule(commandList, PaloAltoPrimative.DELETE, rule);
                }
            }

            boolean status = requestWithCommit(commandList);

            return new Answer(cmd);
        } catch (ExecutionException e) {
            s_logger.error(e);

            if (numRetries > 0 && refreshPaloAltoConnection()) {
                int numRetriesRemaining = numRetries - 1;
                s_logger.debug("Retrying SetStaticNatRulesCommand. Number of retries remaining: " + numRetriesRemaining);
                return execute(cmd, numRetriesRemaining);
            } else {
                return new Answer(cmd, e);
            }
        }
    }

};