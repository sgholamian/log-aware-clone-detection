//,temp,NetscalerResource.java,2786,2812,temp,NetscalerResource.java,2764,2784
//,3
public class xxx {
    private void bindServiceToMonitor(final String nsServiceName, final String nsMonitorName) throws ExecutionException {

        try {
            com.citrix.netscaler.nitro.resource.config.basic.service serviceObject = new com.citrix.netscaler.nitro.resource.config.basic.service();
            serviceObject = com.citrix.netscaler.nitro.resource.config.basic.service.get(_netscalerService, nsServiceName);
            if (serviceObject != null) {
                final com.citrix.netscaler.nitro.resource.config.basic.service_lbmonitor_binding serviceMonitor =
                        new com.citrix.netscaler.nitro.resource.config.basic.service_lbmonitor_binding();
                serviceMonitor.set_monitor_name(nsMonitorName);
                serviceMonitor.set_name(nsServiceName);
                serviceMonitor.set_monstate("ENABLED");
                s_logger.debug("Trying to bind  the monitor :" + nsMonitorName + " to the service :" + nsServiceName);
                com.citrix.netscaler.nitro.resource.config.basic.service_lbmonitor_binding.add(_netscalerService, serviceMonitor);
                s_logger.debug("Successfully binded the monitor :" + nsMonitorName + " to the service :" + nsServiceName);
            }
        } catch (final nitro_exception e) {
            throw new ExecutionException("Failed to create new monitor :" + nsMonitorName + " due to " + e.getMessage());
        } catch (final Exception e) {
            throw new ExecutionException("Failed to create new monitor :" + nsMonitorName + " due to " + e.getMessage());
        }
    }

};