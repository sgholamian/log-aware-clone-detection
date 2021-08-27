//,temp,JMSClientTransactionTest.java,321,345,temp,AMQ2171Test.java,111,141
//,3
public class xxx {
    public void sendMessages(int messageCount) throws JMSException {
        Connection connection = null;
        try {
            connection = createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(getDestinationName());

            for (int i = 0; i < messageCount; ++i) {
                MessageProducer messageProducer = session.createProducer(queue);
                TextMessage message = session.createTextMessage("(" + i + ")");
                message.setIntProperty("sequenceID", i);
                messageProducer.send(message);
                LOG.info("Sent message = {}", message.getText());
            }

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};