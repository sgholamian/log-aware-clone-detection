//,temp,JmsTransactedMessageOrderTest.java,81,110,temp,JMSClientTransactionTest.java,294,319
//,3
public class xxx {
    @Test
    public void testMessageOrderAfterRollback() throws Exception {
        sendMessages(5);

        int counter = 0;
        while (counter++ < 20) {
            LOG.info("Creating connection using prefetch of: {}", prefetch);

            JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI("jms.prefetchPolicy.all=" + prefetch));

            connection = cf.createConnection();
            connection.start();

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue(getDestinationName());
            MessageConsumer consumer = session.createConsumer(queue);

            Message message = consumer.receive(5000);
            assertNotNull(message);
            assertTrue(message instanceof TextMessage);
            LOG.info("Read message = {}", ((TextMessage) message).getText());

            int sequenceID = message.getIntProperty("sequenceID");
            assertEquals(0, sequenceID);

            session.rollback();
            session.close();
            connection.close();
        }
    }

};