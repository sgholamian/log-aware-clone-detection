//,temp,JmsRollbackRedeliveryTest.java,286,319,temp,JmsRollbackRedeliveryTest.java,173,206
//,3
public class xxx {
    @Test
    public void testRedeliveryOnSingleSession() throws Exception {

        ConnectionFactory connectionFactory = 
            new ActiveMQConnectionFactory(brokerUrl); 
        Connection connection = connectionFactory.createConnection();
        connection.start();

        populateDestination(nbMessages, destinationName, connection);

        // Consume messages and rollback transactions
        {
            AtomicInteger received = new AtomicInteger();
            Map<String, Boolean> rolledback = new ConcurrentHashMap<String, Boolean>();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(destinationName);
            while (received.get() < nbMessages) {
                MessageConsumer consumer = session.createConsumer(destination);            
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
                consumer.close();
            }
            session.close();
        }
    }

};