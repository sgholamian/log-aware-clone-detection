//,temp,CamelLogProcessor.java,133,152,temp,LogProcessor.java,91,107
//,3
public class xxx {
    private String fireListeners(Exchange exchange, String message) {
        if (listeners == null || listeners.isEmpty()) {
            return message;
        }
        for (LogListener listener : listeners) {
            if (listener == null) {
                continue;
            }
            try {
                String output = listener.onLog(exchange, logger, message);
                message = output != null ? output : message;
            } catch (Throwable t) {
                LOG.warn("Ignoring an exception thrown by {}: {}", listener.getClass().getName(), t.getMessage());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("", t);
                }
            }
        }
        return message;
    }

};