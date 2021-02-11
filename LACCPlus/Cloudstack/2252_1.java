//,temp,NetscalerResource.java,2814,2833,temp,NetscalerResource.java,1568,1585
//,3
public class xxx {
    private void removeLBMonitor(final String nsMonitorName) throws ExecutionException {

        try {
            if (nsMonitorExist(nsMonitorName)) {
                final lbmonitor monitorObj = lbmonitor.get(_netscalerService, nsMonitorName);
                monitorObj.set_respcode(null);
                lbmonitor.delete(_netscalerService, monitorObj);
                s_logger.info("Successfully deleted monitor : " + nsMonitorName);
            }
        } catch (final nitro_exception e) {
            if (e.getErrorCode() == NitroError.NS_RESOURCE_NOT_EXISTS) {
                return;
            } else {
                throw new ExecutionException("Failed to delete monitor :" + nsMonitorName + " due to " + e.getMessage());
            }
        } catch (final Exception e) {
            throw new ExecutionException("Failed to delete monitor :" + nsMonitorName + " due to " + e.getMessage());
        }

    }

};