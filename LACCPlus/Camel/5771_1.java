//,temp,JmsProducer.java,515,531,temp,SjmsConsumer.java,127,137
//,3
public class xxx {
    protected void testConnectionOnStartup() throws FailedToCreateProducerException {
        try {
            CamelJmsTemplate template = (CamelJmsTemplate) getInOnlyTemplate();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Testing JMS Connection on startup for destination: {}", template.getDefaultDestinationName());
            }

            Connection conn = template.getConnectionFactory().createConnection();
            JmsUtils.closeConnection(conn);

            LOG.debug("Successfully tested JMS Connection on startup for destination: {}",
                    template.getDefaultDestinationName());
        } catch (Exception e) {
            throw new FailedToCreateProducerException(getEndpoint(), e);
        }
    }

};