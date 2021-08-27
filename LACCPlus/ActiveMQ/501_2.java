//,temp,InitialContextTest.java,49,59,temp,InitialContextTest.java,37,47
//,2
public class xxx {
    public void testInitialContext() throws Exception {
        InitialContext context = new InitialContext();
        assertTrue("Created context", context != null);

        ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory)context.lookup("ConnectionFactory");

        assertTrue("Should have created a ConnectionFactory", connectionFactory != null);

        LOG.info("Created with brokerURL: " + connectionFactory.getBrokerURL());

    }

};