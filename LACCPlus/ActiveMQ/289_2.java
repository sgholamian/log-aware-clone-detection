//,temp,AMQ6459Test.java,163,203,temp,JDBCConcurrentDLQTest.java,182,209
//,3
public class xxx {
    private void produceMessages(ActiveMQConnectionFactory amq, Destination dest, int numMessages) throws JMSException {
        Connection connection = amq.createConnection();

        connection.setExceptionListener(new javax.jms.ExceptionListener() {
            public void onException(javax.jms.JMSException e) {
                e.printStackTrace();
            }
        });
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(dest);
        long counter = 0;
        TextMessage message = session.createTextMessage();

        for (int i = 0; i < numMessages; i++) {
            producer.send(message);
            counter++;

            if ((counter % 50) == 0) {
                LOG.info("sent " + counter + " messages");
            }
        }

        if (connection != null) {
            connection.close();
        }
    }

};