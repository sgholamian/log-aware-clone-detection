//,temp,JmsConsumer.java,125,135,temp,SjmsProducer.java,216,232
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateConsumerException {
        try {
            LOG.debug("Testing JMS Connection on startup for destination: {}", getDestinationName());
            Connection con = listenerContainer.getConnectionFactory().createConnection();
            JmsUtils.closeConnection(con);
            LOG.debug("Successfully tested JMS Connection on startup for destination: {}", getDestinationName());
        } catch (Exception e) {
            String msg = "Cannot get JMS Connection on startup for destination " + getDestinationName();
            throw new FailedToCreateConsumerException(getEndpoint(), msg, e);
        }
    }

};