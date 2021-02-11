//,temp,NetScalerControlCenterResource.java,639,650,temp,NetscalerResource.java,3576,3586
//,3
public class xxx {
    private boolean shouldRetry(int numRetries) {
        try {
            if (numRetries > 0) {
                login();
                return true;
            }
        } catch (Exception e) {
            s_logger.error("Failed to log in to Netscaler ControlCenter device at " + _ip + " due to " + e.getMessage());
            return false;
        }
        return false;
    }

};