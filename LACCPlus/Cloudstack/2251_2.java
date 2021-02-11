//,temp,NetscalerResource.java,1600,1615,temp,NetscalerResource.java,1473,1494
//,3
public class xxx {
        private static void deleteVserverServiceBinding(final nitro_service client, final String serviceName, final String vserverName) throws ExecutionException {
            try {
                final gslbvserver_gslbservice_binding[] bindings = gslbvserver_gslbservice_binding.get(client, vserverName);
                if (bindings != null) {
                    for (final gslbvserver_gslbservice_binding binding : bindings) {
                        if (binding.get_servicename().equalsIgnoreCase(serviceName) && binding.get_name().equals(vserverName)) {
                            gslbvserver_gslbservice_binding.delete(client, binding);
                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Successfully deleted service: " + serviceName + " and virtual server: " + vserverName + " binding");
                            }
                            break;
                        }
                    }
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to create service: " + serviceName + " and virtual server: " + vserverName + " binding due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};