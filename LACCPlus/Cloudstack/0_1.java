//,temp,RabbitMQEventBus.java,408,418,temp,RabbitMQEventBus.java,397,406
//,3
public class xxx {
    private synchronized void abortConnection() {
        if (s_connection == null)
            return;

        try {
            s_connection.abort();
        } catch (Exception e) {
            s_logger.warn("Failed to abort connection due to " + e.getMessage());
        }
        s_connection = null;
    }

};