//,temp,NetscalerResource.java,1348,1368,temp,NetscalerResource.java,1230,1256
//,3
public class xxx {
        private static void deleteVirtualServer(final nitro_service client, final String vserverName) throws ExecutionException {
            try {
                final gslbvserver vserver = getVserverObject(client, vserverName);
                if (vserver != null) {
                    gslbvserver.delete(client, vserver);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Successfully deleted GSLB virtual server: " + vserverName);
                    }
                } else {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.warn("Ignoring delete request for non existing  GSLB virtual server: " + vserverName);
                    }
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to delete GSLB virtual server: " + vserverName + " due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};