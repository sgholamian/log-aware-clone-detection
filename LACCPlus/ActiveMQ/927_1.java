//,temp,JmsRollbackRedeliveryTest.java,137,170,temp,JmsRollbackRedeliveryTest.java,97,135
//,3
public class xxx {
    @Test
    public void testRedeliveryOnSingleConsumer() throws Exception {

        ConnectionFactory connectionFactory = 
            new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        populateDestinationWithInterleavedProducer(nbMessages, destinationName, connection);

        // Consume messages and rollback transactions
        {
            AtomicInteger received = new AtomicInteger();
            Map<String, Boolean> rolledback = new ConcurrentHashMap<String, Boolean>();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(destinationName);
            MessageConsumer consumer = session.createConsumer(destination);            
            while (received.get() < nbMessages) {
                TextMessage msg = (TextMessage) consumer.receive(6000000);
                if (msg != null) {
                    if (msg != null && rolledback.put(msg.getText(), Boolean.TRUE) != null) {
                        LOG.info("Received message " + msg.getText() + " (" + received.getAndIncrement() + ")" + msg.getJMSMessageID());
                        assertTrue(msg.getJMSRedelivered());
                        session.commit();
                    } else {
                        LOG.info("Rollback message " + msg.getText() + " id: " +  msg.getJMSMessageID());
                        session.rollback();
                    }
                }
            }
            consumer.close();
            session.close();
        }
    }

};