//,temp,ClusteredRoutePolicy.java,194,205,temp,ServiceRegistrationRoutePolicy.java,72,84
//,3
public class xxx {
    @Override
    public void doStart() throws Exception {
        if (serviceRegistry == null) {
            serviceRegistry = ServiceRegistryHelper.lookupService(camelContext, serviceRegistrySelector).orElseThrow(
                    () -> new IllegalStateException("ServiceRegistry service not found"));
        }

        LOGGER.debug("ServiceRegistrationRoutePolicy {} is using ServiceRegistry instance {} (id={}, type={})",
                this,
                serviceRegistry,
                serviceRegistry.getId(),
                serviceRegistry.getClass().getName());
    }

};