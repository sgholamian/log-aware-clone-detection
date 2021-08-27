//,temp,AMQ4636Test.java,190,228,temp,ConnectionPerMessageTest.java,41,80
//,3
public class xxx {
    public void sendMessage(String topic, String transportURL, boolean transacted, boolean commit)
            throws JMSException {
        Connection connection = null;

        try {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                    transportURL);

            connection = factory.createConnection();
            Session session = connection.createSession(transacted,
                    transacted ? Session.SESSION_TRANSACTED : Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topic);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            Message m = session.createTextMessage("testMessage");
            LOG.info("*** send message to broker...");

            // trigger SQL exception in transactionContext
            throwSQLException = new CountDownLatch(1);
            producer.send(m);

            if (transacted) {
                if (commit) {
                    session.commit();
                } else {
                    session.rollback();
                }
            }

            LOG.info("*** Finished send message to broker");

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};