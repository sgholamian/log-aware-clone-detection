//,temp,AMQ4636Test.java,165,188,temp,AMQ4671Test.java,57,79
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testNonDurableSubscriberInvalidUnsubscribe() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUri);

        Connection connection = connectionFactory.createConnection();
        connection.setClientID(getClass().getName());
        connection.start();

        try {
            Session ts = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            try {
                ts.unsubscribe("invalid-subscription-name");
                fail("this should fail");
            } catch (javax.jms.InvalidDestinationException e) {
                LOG.info("Test caught correct invalid destination exception");
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};