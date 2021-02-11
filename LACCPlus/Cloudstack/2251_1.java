//,temp,NetscalerResource.java,1600,1615,temp,NetscalerResource.java,1473,1494
//,3
public class xxx {
        private static void deleteGslbServiceGslbMonitorBinding(final nitro_service nsService, final String monitorName, final String serviceName) {
            try {
                final gslbservice_lbmonitor_binding[] monitorBindings = gslbservice_lbmonitor_binding.get(nsService, serviceName);
                if (monitorBindings != null && monitorBindings.length > 0) {
                    for (final gslbservice_lbmonitor_binding binding : monitorBindings) {
                        if (binding.get_monitor_name().equalsIgnoreCase(monitorName)) {
                            s_logger.info("Found a binding between monitor " + binding.get_monitor_name() + " and " + binding.get_servicename());
                            gslbservice_lbmonitor_binding.delete(nsService, binding);
                        }
                    }
                }
            } catch (final Exception e) {
                s_logger.debug("Failed to delete GSLB monitor " + monitorName + " and GSLB service " + serviceName + " binding due to " + e.getMessage() +
                        " but moving on ..., will be cleaned up as part of GSLB " + " service delete any way..");
            }
        }

};