//,temp,JmsTransactedMessageOrderTest.java,81,110,temp,JMSClientTransactionTest.java,294,319
//,3
public class xxx {
    @Test
    public void testMessageOrderAfterRollback() throws Exception {
        sendMessages(5);

        int counter = 0;
        while (counter++ < 10) {
            connection = createConnection();
            connection.start();

            Session session = connection.createSession(true, -1);
            Queue queue = session.createQueue(getDestinationName());
            MessageConsumer consumer = session.createConsumer(queue);

            Message message = consumer.receive(5000);
            assertNotNull(message);
            assertTrue(message instanceof TextMessage);

            int sequenceID = message.getIntProperty("sequenceID");
            assertEquals(0, sequenceID);

            LOG.info("Read message = {}", ((TextMessage) message).getText());
            session.rollback();
            session.close();
            connection.close();
        }
    }

};