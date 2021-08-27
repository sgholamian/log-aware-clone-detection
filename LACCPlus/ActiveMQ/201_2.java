//,temp,DurableSubscriptionHangTestCase.java,97,109,temp,AMQ6387Test.java,173,185
//,3
public class xxx {
    private void createDurableSubscription() throws JMSException {
        final Connection connection = connectionFactory.createConnection();
        connection.setClientID(CLIENT_ID);
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Topic topic = session.createTopic(TOPIC_NAME);
        connection.start();

        session.createDurableSubscriber(topic, SUBSCRIPTION_NAME, null, false);
        LOG.info("Created durable subscription.");

        connection.stop();
        connection.close();
    }

};