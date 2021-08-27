//,temp,JmxManagementLifecycleStrategy.java,696,729,temp,JmxManagementLifecycleStrategy.java,435,468
//,3
public class xxx {
    @Override
    public void onServiceAdd(CamelContext context, Service service, Route route) {
        if (!initialized) {
            // pre register so we can register later when we have been initialized
            preServices.add(lf -> lf.onServiceAdd(camelContext, service, route));
            return;
        }

        // services can by any kind of misc type but also processors
        // so we have special logic when its a processor

        if (!shouldRegister(service, route)) {
            // avoid registering if not needed
            return;
        }

        Object managedObject = getManagedObjectForService(context, service, route);
        if (managedObject == null) {
            // service should not be managed
            return;
        }

        // skip already managed services, for example if a route has been restarted
        if (getManagementStrategy().isManaged(managedObject)) {
            LOG.trace("The service is already managed: {}", service);
            return;
        }

        try {
            manageObject(managedObject);
        } catch (Exception e) {
            LOG.warn("Could not register service: " + service + " as Service MBean.", e);
        }
    }

};