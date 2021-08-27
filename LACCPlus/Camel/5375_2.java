//,temp,HazelcastRoutePolicy.java,179,189,temp,EtcdRoutePolicy.java,181,193
//,3
public class xxx {
    private void stopConsumer(Route route) {
        synchronized (lock) {
            try {
                if (!suspendedRoutes.contains(route)) {
                    LOGGER.debug("Stopping consumer for {} ({})", route.getId(), route.getConsumer());
                    stopConsumer(route.getConsumer());
                    suspendedRoutes.add(route);
                }
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

};