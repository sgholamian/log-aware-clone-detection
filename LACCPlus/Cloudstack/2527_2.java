//,temp,NetscalerResource.java,1473,1494,temp,NetscalerResource.java,1230,1256
//,3
public class xxx {
        private static void deleteSite(final nitro_service client, final String siteName) throws ExecutionException {
            try {
                final gslbsite site = getSiteObject(client, siteName);
                if (site != null) {
                    final gslbsite_gslbservice_binding[] serviceBindings = gslbsite_gslbservice_binding.get(client, siteName);
                    if (serviceBindings != null && serviceBindings.length > 0) {
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("There are services associated with GSLB site: " + siteName + " so ignoring site deletion");
                        }
                    }
                    gslbsite.delete(client, siteName);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Successfully deleted GSLB site: " + siteName);
                    }
                } else {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.warn("Ignoring delete request for non existing  GSLB site: " + siteName);
                    }
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to delete GSLB site: " + siteName + " due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};