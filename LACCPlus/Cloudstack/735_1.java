//,temp,NetscalerResource.java,1421,1441,temp,NetscalerResource.java,1348,1368
//,2
public class xxx {
        private static void deleteService(final nitro_service client, final String serviceName) throws ExecutionException {
            try {
                final gslbservice service = getServiceObject(client, serviceName);
                if (service != null) {
                    gslbservice.delete(client, serviceName);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Successfully deleted service: " + serviceName);
                    }
                } else {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.warn("Ignoring delete request for non existing  service: " + serviceName);
                    }
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to delete service: " + serviceName + " due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};