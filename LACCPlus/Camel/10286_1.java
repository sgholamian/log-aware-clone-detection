//,temp,CamelContextTracker.java,106,116,temp,CamelContextTracker.java,94,104
//,2
public class xxx {
    public static synchronized void notifyContextDestroyed(CamelContext camelContext) {
        for (CamelContextTracker tracker : TRACKERS) {
            try {
                if (tracker.accept(camelContext)) {
                    tracker.contextDestroyed(camelContext);
                }
            } catch (Exception e) {
                LOG.warn("Error calling CamelContext tracker. This exception is ignored.", e);
            }
        }
    }

};