//,temp,F5BigIpResource.java,263,267,temp,BigSwitchBcfResource.java,577,580
//,3
public class xxx {
    private Answer retry(Command cmd, int numRetries) {
        int numRetriesRemaining = numRetries - 1;
        s_logger.error("Retrying " + cmd.getClass().getSimpleName() + ". Number of retries remaining: " + numRetriesRemaining);
        return executeRequest(cmd, numRetriesRemaining);
    }

};