//,temp,F5BigIpResource.java,269,279,temp,NetscalerResource.java,3576,3586
//,2
public class xxx {
    private boolean shouldRetry(final int numRetries) {
        try {
            if (numRetries > 0) {
                login();
                return true;
            }
        } catch (final Exception e) {
            s_logger.error("Failed to log in to Netscaler device at " + _ip + " due to " + e.getMessage());
        }
        return false;
    }

};