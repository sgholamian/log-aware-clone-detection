//,temp,JMSClientTransactionTest.java,122,158,temp,JMSClientTransactionTest.java,85,120
//,2
public class xxx {
    @Test(timeout = 60000)
    public void testSingleConsumedMessagePerTxCase() throws Exception {
        connection = createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(getTestName());
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 0; i < MSG_COUNT; i++) {
            TextMessage message = session.createTextMessage();
            message.setText("test" + i);
            messageProducer.send(message, DeliveryMode.PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY, javax.jms.Message.DEFAULT_TIME_TO_LIVE);
        }

        session.close();

        QueueViewMBean queueView = getProxyToQueue(getTestName());
        assertEquals(1000, queueView.getQueueSize());

        int counter = 0;
        session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        do {
            TextMessage message = (TextMessage) messageConsumer.receive(1000);
            if (message != null) {
                counter++;
                LOG.info("Message n. {} with content '{}' has been recieved.", counter, message.getText());
                session.commit();
                LOG.info("Transaction has been committed.");
            }
        } while (counter < MSG_COUNT);

        assertEquals(0, queueView.getQueueSize());

        session.close();
    }

};