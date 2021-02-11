//,temp,F5BigIpResource.java,263,267,temp,NetscalerResource.java,3570,3574
//,2
public class xxx {
    private Answer retry(final Command cmd, final int numRetries) {
        final int numRetriesRemaining = numRetries - 1;
        s_logger.warn("Retrying " + cmd.getClass().getSimpleName() + ". Number of retries remaining: " + numRetriesRemaining);
        return executeRequest(cmd, numRetriesRemaining);
    }

};