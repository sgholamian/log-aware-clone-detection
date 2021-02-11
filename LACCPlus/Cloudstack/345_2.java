//,temp,NetscalerResource.java,1497,1521,temp,NetscalerResource.java,1443,1471
//,3
public class xxx {
        private static void createVserverServiceBinding(final nitro_service client, final String serviceName, final String vserverName, final long weight) throws ExecutionException {
            String errMsg;
            try {
                assert weight >= 1 && weight <= 100;
                final gslbvserver_gslbservice_binding binding = new gslbvserver_gslbservice_binding();
                binding.set_name(vserverName);
                binding.set_servicename(serviceName);
                binding.set_weight(weight);
                gslbvserver_gslbservice_binding.add(client, binding);
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully created service: " + serviceName + " and virtual server: " + vserverName + " binding");
                }
            } catch (final nitro_exception ne) {
                if (ne.getErrorCode() == 273) {
                    return;
                }
                errMsg = "Failed to create service: " + serviceName + " and virtual server: " + vserverName + " binding due to " + ne.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            } catch (final Exception e) {
                errMsg = "Failed to create service: " + serviceName + " and virtual server: " + vserverName + " binding due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};