//,temp,BrocadeVcsResource.java,278,281,temp,NetScalerControlCenterResource.java,633,637
//,3
public class xxx {
    private Answer retry(Command cmd, int numRetries) {
        s_logger.warn("Retrying " + cmd.getClass().getSimpleName() + ". Number of retries remaining: " + numRetries);
        return executeRequest(cmd, numRetries);
    }

};