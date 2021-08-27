//,temp,JmsRollbackRedeliveryTest.java,241,271,temp,JmsRollbackRedeliveryTest.java,209,238
//,3
public class xxx {
    @Test
    public void testValidateRedeliveryCountOnRollbackWithPrefetch0() throws Exception {

       final int numMessages = 1;
       ConnectionFactory connectionFactory = 
            new ActiveMQConnectionFactory(brokerUrl + "?jms.prefetchPolicy.queuePrefetch=0");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        populateDestination(numMessages, destinationName, connection);

        {
            AtomicInteger received = new AtomicInteger();
            final int maxRetries = new RedeliveryPolicy().getMaximumRedeliveries();
            while (received.get() < maxRetries) {
                Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                Destination destination = session.createQueue(destinationName);

                MessageConsumer consumer = session.createConsumer(destination);            
                TextMessage msg = (TextMessage) consumer.receive(1000);
                if (msg != null) {
                    LOG.info("Received message " + msg.getText() + " (" + received.getAndIncrement() + ")" + msg.getJMSMessageID());
                    assertEquals("redelivery property matches deliveries", received.get(), msg.getLongProperty("JMSXDeliveryCount"));
                    session.rollback();
                }
                session.close();
            }
            
            consumeMessage(connection, maxRetries + 1);
        }
    }

};