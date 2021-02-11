//,temp,JuniperSrxResource.java,517,528,temp,F5BigIpResource.java,269,279
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