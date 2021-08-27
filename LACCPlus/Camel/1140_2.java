//,temp,JmxManagementLifecycleStrategy.java,731,752,temp,JmxManagementLifecycleStrategy.java,665,694
//,3
public class xxx {
    @Override
    public void onRoutesRemove(Collection<Route> routes) {
        // the agent hasn't been started
        if (!initialized) {
            return;
        }

        for (Route route : routes) {
            Object mr = getManagementObjectStrategy().getManagedObjectForRoute(camelContext, route);

            // skip unmanaged routes
            if (!getManagementStrategy().isManaged(mr)) {
                LOG.trace("The route is not managed: {}", route);
                continue;
            }

            try {
                unmanageObject(mr);
            } catch (Exception e) {
                LOG.warn("Could not unregister Route MBean", e);
            }

            // remove from known routes ids, as the route has been removed
            knowRouteIds.remove(route.getId());
        }

        // after the routes has been removed, we should clear the wrapped processors as we no longer need them
        // as they were just a provisional map used during creation of routes
        removeWrappedProcessorsForRoutes(routes);
    }

};