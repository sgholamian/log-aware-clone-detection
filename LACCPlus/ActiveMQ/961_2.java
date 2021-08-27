//,temp,AmqpScheduledMessageTest.java,266,288,temp,XACompletionTest.java,565,585
//,3
public class xxx {
    private boolean consumeOnlyN(int expected, String clientId, String subName, ActiveMQTopic destination) throws Exception {
        int drained = 0;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri + "?jms.prefetchPolicy.all=" + expected);
        factory.setWatchTopicAdvisories(false);
        javax.jms.Connection connection = factory.createConnection();
        connection.setClientID(clientId);
        try {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createDurableSubscriber(destination, subName);
            Message message = null;
            while ( (message =consumer.receive(2000)) != null) {
                drained++;
                LOG.info("Sub:" + subName + ", received: " + message.getJMSMessageID());
            }
            consumer.close();
        } finally {
            connection.close();
        }
        return drained == expected;
    }

};