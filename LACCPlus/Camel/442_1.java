//,temp,HazelcastRoutePolicy.java,191,202,temp,EtcdRoutePolicy.java,195,208
//,3
public class xxx {
    private synchronized void startAllStoppedConsumers() {
        try {
            for (Route route : suspendedRoutes) {
                LOGGER.debug("Starting consumer for {} ({})", route.getId(), route.getConsumer());
                startConsumer(route.getConsumer());
            }

            suspendedRoutes.clear();
        } catch (Exception e) {
            handleException(e);
        }
    }

};