//,temp,NetscalerResource.java,2786,2812,temp,NetscalerResource.java,2764,2784
//,3
public class xxx {
    private void unBindServiceToMonitor(final String nsServiceName, final String nsMonitorName) throws ExecutionException {

        try {
            com.citrix.netscaler.nitro.resource.config.basic.service serviceObject = new com.citrix.netscaler.nitro.resource.config.basic.service();
            serviceObject = com.citrix.netscaler.nitro.resource.config.basic.service.get(_netscalerService, nsServiceName);

            if (serviceObject != null) {
                final com.citrix.netscaler.nitro.resource.config.basic.service_lbmonitor_binding serviceMonitor =
                        new com.citrix.netscaler.nitro.resource.config.basic.service_lbmonitor_binding();
                serviceMonitor.set_monitor_name(nsMonitorName);
                serviceMonitor.set_name(nsServiceName);
                s_logger.debug("Trying to unbind  the monitor :" + nsMonitorName + " from the service :" + nsServiceName);
                service_lbmonitor_binding.delete(_netscalerService, serviceMonitor);
                s_logger.debug("Successfully unbinded the monitor :" + nsMonitorName + " from the service :" + nsServiceName);
            }

        } catch (final nitro_exception e) {
            if (e.getErrorCode() == NitroError.NS_RESOURCE_NOT_EXISTS) {
                return;
            } else {
                throw new ExecutionException("Failed to unbind monitor :" + nsMonitorName + "from the service :" + nsServiceName + "due to " + e.getMessage());
            }
        } catch (final Exception e) {
            throw new ExecutionException("Failed to unbind monitor :" + nsMonitorName + "from the service :" + nsServiceName + "due to " + e.getMessage());
        }

    }

};