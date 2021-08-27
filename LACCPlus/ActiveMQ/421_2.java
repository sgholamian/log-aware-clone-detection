//,temp,NonBlockingConsumerRedeliveryTest.java,185,239,temp,NonBlockingConsumerRedeliveryTest.java,115,183
//,3
public class xxx {
    @Test
    public void testMessageDeleiveredInCorrectOrder() throws Exception {

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

        Iterator<Message> after = afterRollback.iterator();
        Iterator<Message> before = beforeRollback.iterator();

        while (before.hasNext() && after.hasNext()) {
            TextMessage original = (TextMessage) before.next();
            TextMessage rolledBack = (TextMessage) after.next();

            int originalInt = Integer.parseInt(original.getText());
            int rolledbackInt = Integer.parseInt(rolledBack.getText());

            assertEquals(originalInt, rolledbackInt);
        }

        session.commit();
    }

};