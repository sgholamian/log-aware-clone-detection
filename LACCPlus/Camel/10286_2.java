//,temp,CamelContextTracker.java,106,116,temp,CamelContextTracker.java,94,104
//,2
public class xxx {
    public static synchronized void notifyContextCreated(CamelContext camelContext) {
        for (CamelContextTracker tracker : TRACKERS) {
            try {
                if (tracker.accept(camelContext)) {
                    tracker.contextCreated(camelContext);
                }
            } catch (Exception e) {
                LOG.warn("Error calling CamelContext tracker. This exception is ignored.", e);
            }
        }
    }

};