//,temp,ServiceHelper.java,288,311,temp,ServiceHelper.java,179,199
//,3
public class xxx {
    public static void stopService(Collection<?> services) {
        if (services == null) {
            return;
        }
        RuntimeException firstException = null;
        for (Object value : services) {
            try {
                stopService(value);
            } catch (RuntimeException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Caught exception stopping service: {}", value, e);
                }
                if (firstException == null) {
                    firstException = e;
                }
            }
        }
        if (firstException != null) {
            throw firstException;
        }
    }

};