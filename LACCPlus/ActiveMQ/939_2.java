//,temp,JMSClientTransactionTest.java,321,345,temp,AMQ2171Test.java,111,141
//,3
public class xxx {
    private static void produce(String brokerURL, int count) throws Exception {
        Connection connection = null;

        try {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            producer.setTimeToLive(0);
            connection.start();

            for (int i = 0; i < count; i++) {
                int id = i + 1;
                TextMessage message = session.createTextMessage("Message " + id);
                message.setIntProperty("MsgNumber", id);
                producer.send(message);

                if (id % 500 == 0) {
                    LOG.info("sent " + id + ", ith " + message);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Throwable e) {
            }
        }
    }

};