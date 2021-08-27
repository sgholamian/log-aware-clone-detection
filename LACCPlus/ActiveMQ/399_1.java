//,temp,AMQ5863CompositePublishTest.java,86,110,temp,JmsSendReceiveStressTest.java,124,140
//,3
public class xxx {
    @Test
    public void test() throws Exception {

        ActiveMQQueue compositeSendTo = new ActiveMQQueue("one,two,three");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setWatchTopicAdvisories(false);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        try {

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            session.createProducer(compositeSendTo).send(session.createTextMessage("Bing"));

            for (ActiveMQDestination dest : compositeSendTo.getCompositeDestinations()) {
                Message message = session.createConsumer(dest).receive(5000);
                LOG.info("From: " + dest + ", " + message.getJMSDestination());
                assertNotNull("got message from: " + dest, message);
            }

        } finally {
            connection.close();
        }
    }

};