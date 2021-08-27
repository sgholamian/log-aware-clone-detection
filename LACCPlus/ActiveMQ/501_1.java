//,temp,InitialContextTest.java,49,59,temp,InitialContextTest.java,37,47
//,2
public class xxx {
    public void testInitialContextHasXA() throws Exception {
        InitialContext context = new InitialContext();
        assertTrue("Created context", context != null);

        ActiveMQXAConnectionFactory connectionFactory = (ActiveMQXAConnectionFactory)context.lookup("XAConnectionFactory");

        assertTrue("Should have created an XAConnectionFactory", connectionFactory != null);

        LOG.info("Created with brokerURL: " + connectionFactory.getBrokerURL());

    }

};