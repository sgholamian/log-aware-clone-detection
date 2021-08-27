//,temp,JmsConsumer.java,125,135,temp,SjmsProducer.java,216,232
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateProducerException {
        try {
            SjmsTemplate template = getInOnlyTemplate();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Testing JMS Connection on startup for destination: {}", getEndpoint().getDestinationName());
            }

            Connection conn = template.getConnectionFactory().createConnection();
            SjmsHelper.closeConnection(conn);

            LOG.debug("Successfully tested JMS Connection on startup for destination: {}",
                    getEndpoint().getDestinationName());
        } catch (Exception e) {
            throw new FailedToCreateProducerException(getEndpoint(), e);
        }
    }

};