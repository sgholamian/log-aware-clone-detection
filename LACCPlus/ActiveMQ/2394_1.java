//,temp,NonBlockingConsumerRedeliveryTest.java,360,424,temp,NonBlockingConsumerRedeliveryTest.java,287,358
//,3
public class xxx {
    @Test
    public void testNonBlockingMessageDeleiveryWithAllRolledBack() throws Exception {
        final LinkedHashSet<Message> received = new LinkedHashSet<Message>();
        final LinkedHashSet<Message> dlqed = new LinkedHashSet<Message>();

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.getRedeliveryPolicy().setMaximumRedeliveries(5);
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue(destinationName);
        final Destination dlq = session.createQueue("ActiveMQ.DLQ");
        final MessageConsumer consumer = session.createConsumer(destination);
        final MessageConsumer dlqConsumer = session.createConsumer(dlq);

        dlqConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                dlqed.add(message);
            }
        });

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                received.add(message);
            }
        });

        sendMessages();
        connection.start();

        assertTrue("Pre-Rollback expects to receive: " + MSG_COUNT + " messages.",
            Wait.waitFor(new Wait.Condition(){
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages.");
                    return received.size() == MSG_COUNT;
                }
            }
        ));

        session.rollback();

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    session.rollback();
                } catch (JMSException e) {
                    LOG.warn("Caught an unexcepted exception: " + e.getMessage());
                }
            }
        });

        assertTrue("Post-Rollback expects to DLQ: " + MSG_COUNT + " messages.",
            Wait.waitFor(new Wait.Condition(){
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + dlqed.size() + " messages in DLQ.");
                    return dlqed.size() == MSG_COUNT;
                }
            }
        ));

        session.commit();
    }

};