//,temp,PaloAltoResource.java,628,656,temp,PaloAltoResource.java,552,579
//,2
public class xxx {
    private Answer execute(SetPortForwardingRulesCommand cmd, int numRetries) {
        PortForwardingRuleTO[] rules = cmd.getRules();

        try {
            ArrayList<IPaloAltoCommand> commandList = new ArrayList<IPaloAltoCommand>();

            for (PortForwardingRuleTO rule : rules) {
                if (!rule.revoked()) {
                    manageDstNatRule(commandList, PaloAltoPrimative.ADD, rule);
                } else {
                    manageDstNatRule(commandList, PaloAltoPrimative.DELETE, rule);
                }
            }

            boolean status = requestWithCommit(commandList);

            return new Answer(cmd);
        } catch (ExecutionException e) {
            s_logger.error(e);

            if (numRetries > 0 && refreshPaloAltoConnection()) {
                int numRetriesRemaining = numRetries - 1;
                s_logger.debug("Retrying SetPortForwardingRulesCommand. Number of retries remaining: " + numRetriesRemaining);
                return execute(cmd, numRetriesRemaining);
            } else {
                return new Answer(cmd, e);
            }
        }
    }

};