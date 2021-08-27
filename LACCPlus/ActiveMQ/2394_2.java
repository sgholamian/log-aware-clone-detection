//,temp,NonBlockingConsumerRedeliveryTest.java,360,424,temp,NonBlockingConsumerRedeliveryTest.java,287,358
//,3
public class xxx {
    @Test
    public void testNonBlockingMessageDeleiveryWithRollbacks() throws Exception {
        final LinkedHashSet<Message> received = new LinkedHashSet<Message>();

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue(destinationName);
        final MessageConsumer consumer = session.createConsumer(destination);

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

        received.clear();

        consumer.setMessageListener(new MessageListener() {

            int count = 0;

            @Override
            public void onMessage(Message message) {

                if (++count > 10) {
                    try {
                        session.rollback();
                        LOG.info("Rolling back session.");
                        count = 0;
                    } catch (JMSException e) {
                        LOG.warn("Caught an unexcepted exception: " + e.getMessage());
                    }
                } else {
                    received.add(message);
                    try {
                        session.commit();
                    } catch (JMSException e) {
                        LOG.warn("Caught an unexcepted exception: " + e.getMessage());
                    }
                }
            }
        });

        session.rollback();

        assertTrue("Post-Rollback expects to receive: " + MSG_COUNT + " messages.",
            Wait.waitFor(new Wait.Condition(){
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages since rollback.");
                    return received.size() == MSG_COUNT;
                }
            }
        ));

        assertEquals(MSG_COUNT, received.size());
        session.commit();
    }

};