//,temp,JmsProducer.java,515,531,temp,SjmsConsumer.java,127,137
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateConsumerException {
        try {
            LOG.debug("Testing JMS Connection on startup for destination: {}", getDestinationName());
            Connection con = listenerContainer.getConnectionFactory().createConnection();
            closeConnection(con);
            LOG.debug("Successfully tested JMS Connection on startup for destination: {}", getDestinationName());
        } catch (Exception e) {
            String msg = "Cannot get JMS Connection on startup for destination " + getDestinationName();
            throw new FailedToCreateConsumerException(getEndpoint(), msg, e);
        }
    }

};