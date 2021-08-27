//,temp,JmsRollbackRedeliveryTest.java,137,170,temp,JmsRollbackRedeliveryTest.java,97,135
//,3
public class xxx {
    public void doTestRedelivery(String brokerUrl, boolean interleaveProducer) throws Exception {
        LOG.debug("entering doTestRedelivery interleaveProducer is " + interleaveProducer);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        
        Connection connection = connectionFactory.createConnection();
        connection.start();

        if (interleaveProducer) {
            populateDestinationWithInterleavedProducer(nbMessages, destinationName, connection);
        } else {
            populateDestination(nbMessages, destinationName, connection);
        }
        
        // Consume messages and rollback transactions
        {
            AtomicInteger received = new AtomicInteger();
            Map<String, Boolean> rolledback = new ConcurrentHashMap<String, Boolean>();
            while (received.get() < nbMessages) {
                Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(destinationName);
                MessageConsumer consumer = session.createConsumer(destination);
                TextMessage msg = (TextMessage) consumer.receive(6000000);
                if (msg != null) {
                    if (msg != null && rolledback.put(msg.getText(), Boolean.TRUE) != null) {
                        LOG.info("Received message " + msg.getText() + " (" + received.getAndIncrement() + ")" + msg.getJMSMessageID());
                        assertTrue(msg.getJMSRedelivered());
                        assertEquals(2, msg.getLongProperty("JMSXDeliveryCount"));
                        session.commit();
                    } else {
                        LOG.info("Rollback message " + msg.getText() + " id: " +  msg.getJMSMessageID());
                        assertFalse("should not have redelivery flag set, id: " + msg.getJMSMessageID(), msg.getJMSRedelivered());
                        session.rollback();
                    }
                }
                consumer.close();
                session.close();
            }
        }
    }

};