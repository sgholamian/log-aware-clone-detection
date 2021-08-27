//,temp,SpringRabbitMQConsumer.java,79,94,temp,SpringRabbitMQProducer.java,226,243
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateProducerException {
        Connection conn = null;
        try {
            RabbitTemplate template = getInOnlyTemplate();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Testing RabbitMQ Connection on startup for: {}", getEndpoint().getConnectionFactory().getHost());
            }
            conn = template.getConnectionFactory().createConnection();

            LOG.debug("Successfully tested RabbitMQ Connection on startup for: {}",
                    getEndpoint().getConnectionFactory().getHost());
        } catch (Exception e) {
            throw new FailedToCreateProducerException(getEndpoint(), e);
        } finally {
            RabbitUtils.closeConnection(conn);
        }
    }

};