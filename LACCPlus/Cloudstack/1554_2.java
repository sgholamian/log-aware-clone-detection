//,temp,BigSwitchBcfResource.java,577,580,temp,NetScalerControlCenterResource.java,633,637
//,3
public class xxx {
    private Answer retry(Command cmd, int numRetries) {
        int numRetriesRemaining = numRetries - 1;
        s_logger.warn("Retrying " + cmd.getClass().getSimpleName() + ". Number of retries remaining: " + numRetriesRemaining);
        return executeRequest(cmd, numRetriesRemaining);
    }

};