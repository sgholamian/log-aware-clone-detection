//,temp,F5BigIpResource.java,269,279,temp,NetScalerControlCenterResource.java,639,650
//,3
public class xxx {
    private boolean shouldRetry(int numRetries) {
        try {
            if (numRetries > 0) {
                login();
                return true;
            }
        } catch (Exception e) {
            s_logger.error("Failed to log in to F5 device at " + _ip + " due to " + e.getMessage());
        }
        return false;
    }

};