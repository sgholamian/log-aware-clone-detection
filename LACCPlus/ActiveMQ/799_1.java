//,temp,AMQ4636Test.java,165,188,temp,AMQ4671Test.java,57,79
//,3
public class xxx {
    public void createDurableConsumer(String topic,
                                      String transportURL) throws JMSException {
        Connection connection = null;
        LOG.info("*** createDurableConsumer() called ...");

        try {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                    transportURL);

            connection = factory.createConnection();
            connection.setClientID("myconn1");
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topic);

            TopicSubscriber topicSubscriber = session.createDurableSubscriber(
                    (Topic) destination, "MySub1");
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};