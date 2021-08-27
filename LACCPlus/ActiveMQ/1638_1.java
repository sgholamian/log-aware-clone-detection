//,temp,JmsRollbackRedeliveryTest.java,286,319,temp,JmsRollbackRedeliveryTest.java,173,206
//,3
public class xxx {
    @Test
    public void testRedeliveryPropertyWithNoRollback() throws Exception {
        final int numMessages = 1;
        ConnectionFactory connectionFactory = 
            new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        populateDestination(numMessages, destinationName, connection);
        connection.close();
        
        {
            AtomicInteger received = new AtomicInteger();
            final int maxRetries = new RedeliveryPolicy().getMaximumRedeliveries();
            while (received.get() < maxRetries) {
                connection = connectionFactory.createConnection();
                connection.start();
                Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                Destination destination = session.createQueue(destinationName);

                MessageConsumer consumer = session.createConsumer(destination);            
                TextMessage msg = (TextMessage) consumer.receive(2000);
                if (msg != null) {
                    LOG.info("Received message " + msg.getText() + " (" + received.getAndIncrement() + ")" + msg.getJMSMessageID());
                    assertEquals("redelivery property matches deliveries", received.get(), msg.getLongProperty("JMSXDeliveryCount"));
                }
                session.close();
                connection.close();
            }
            connection = connectionFactory.createConnection();
            connection.start();
            consumeMessage(connection, maxRetries + 1);
        }
    }

};