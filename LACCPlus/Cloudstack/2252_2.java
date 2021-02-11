//,temp,NetscalerResource.java,2814,2833,temp,NetscalerResource.java,1568,1585
//,3
public class xxx {
        private static void deleteGslbServiceMonitor(final nitro_service nsService, final String monitorName) throws ExecutionException {
            try {
                final lbmonitor serviceMonitor = lbmonitor.get(nsService, monitorName);
                if (serviceMonitor != null) {
                    lbmonitor.delete(nsService, serviceMonitor);
                }
            } catch (final nitro_exception ne) {
                if (ne.getErrorCode() != NitroError.NS_RESOURCE_NOT_EXISTS) {
                    final String errMsg = "Failed to delete monitor " + monitorName + " for GSLB service due to " + ne.getMessage();
                    s_logger.debug(errMsg);
                    throw new com.cloud.utils.exception.ExecutionException(errMsg);
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to delete monitor " + monitorName + " for GSLB service due to " + e.getMessage();
                s_logger.debug(errMsg);
                throw new com.cloud.utils.exception.ExecutionException(errMsg);
            }
        }

};