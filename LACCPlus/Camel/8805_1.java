//,temp,SpringRabbitMQConsumer.java,79,94,temp,SpringRabbitMQProducer.java,226,243
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateConsumerException {
        Connection conn = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Testing RabbitMQ Connection on startup for: {}", getEndpoint().getConnectionFactory().getHost());
            }
            conn = listenerContainer.getConnectionFactory().createConnection();

            LOG.debug("Successfully tested RabbitMQ Connection on startup for: {}",
                    getEndpoint().getConnectionFactory().getHost());
        } catch (Exception e) {
            throw new FailedToCreateConsumerException(getEndpoint(), e);
        } finally {
            RabbitUtils.closeConnection(conn);
        }
    }

};