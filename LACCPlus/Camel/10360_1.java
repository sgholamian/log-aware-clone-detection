//,temp,DefaultShutdownStrategy.java,443,456,temp,DefaultShutdownStrategy.java,422,435
//,2
public class xxx {
    protected void suspendNow(String routeId, Consumer consumer) {
        LOG.trace("Suspending: {}", consumer);

        // allow us to do custom work before delegating to service helper
        try {
            ServiceHelper.suspendService(consumer);
        } catch (Throwable e) {
            LOG.warn("Error occurred while suspending route: " + routeId + ". This exception will be ignored.", e);
            // fire event
            EventHelper.notifyServiceStopFailure(consumer.getEndpoint().getCamelContext(), consumer, e);
        }

        LOG.trace("Suspend complete for: {}", consumer);
    }

};