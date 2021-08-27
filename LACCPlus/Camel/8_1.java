//,temp,ServicePool.java,376,385,temp,ServicePool.java,280,289
//,2
public class xxx {
        void doStop(Service s) {
            if (s != null) {
                ServicePool.stop(s);
                try {
                    endpoint.getCamelContext().removeService(s);
                } catch (Exception e) {
                    LOG.debug("Error removing service: {}. This exception is ignored.", s, e);
                }
            }
        }

};