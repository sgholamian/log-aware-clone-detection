//,temp,ServiceHelper.java,288,311,temp,ServiceHelper.java,179,199
//,3
public class xxx {
    public static void resumeServices(Collection<?> services) {
        if (services == null) {
            return;
        }
        RuntimeException firstException = null;
        for (Object value : services) {
            if (value instanceof Service) {
                Service service = (Service) value;
                try {
                    resumeService(service);
                } catch (RuntimeException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Caught exception resuming service: {}", service, e);
                    }
                    if (firstException == null) {
                        firstException = e;
                    }
                }
            }
        }
        if (firstException != null) {
            throw firstException;
        }
    }

};