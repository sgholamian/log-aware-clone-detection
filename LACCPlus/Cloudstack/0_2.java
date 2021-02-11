//,temp,RabbitMQEventBus.java,408,418,temp,RabbitMQEventBus.java,397,406
//,3
public class xxx {
    private synchronized void closeConnection() {
        try {
            if (s_connection != null) {
                s_connection.close();
            }
        } catch (Exception e) {
            s_logger.warn("Failed to close connection to AMQP server due to " + e.getMessage());
        }
        s_connection = null;
    }

};