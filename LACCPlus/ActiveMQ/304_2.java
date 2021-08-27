//,temp,NonBlockingConsumerRedeliveryTest.java,241,285,temp,NonBlockingConsumerRedeliveryTest.java,59,113
//,3
public class xxx {
    @Test
    public void testMessageDeleiveredWhenNonBlockingEnabled() throws Exception {

        final LinkedHashSet<Message> received = new LinkedHashSet<Message>();
        final LinkedHashSet<Message> beforeRollback = new LinkedHashSet<Message>();
        final LinkedHashSet<Message> afterRollback = new LinkedHashSet<Message>();

        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(destinationName);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                received.add(message);
            }
        });

        sendMessages();

        session.commit();
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

        beforeRollback.addAll(received);
        received.clear();
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

        afterRollback.addAll(received);
        received.clear();

        assertEquals(beforeRollback.size(), afterRollback.size());
        assertEquals(beforeRollback, afterRollback);
        session.commit();
    }

};