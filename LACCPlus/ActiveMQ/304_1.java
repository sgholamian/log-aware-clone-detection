//,temp,NonBlockingConsumerRedeliveryTest.java,241,285,temp,NonBlockingConsumerRedeliveryTest.java,59,113
//,3
public class xxx {
    @Test
    public void testNonBlockingMessageDeleiveryIsDelayed() throws Exception {
        final LinkedHashSet<Message> received = new LinkedHashSet<Message>();

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.getRedeliveryPolicy().setInitialRedeliveryDelay(TimeUnit.SECONDS.toMillis(6));
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
        session.rollback();

        assertFalse("Delayed redelivery test not expecting any messages yet.",
            Wait.waitFor(new Wait.Condition(){
                @Override
                public boolean isSatisified() throws Exception {
                    return received.size() > 0;
                }
            }, TimeUnit.SECONDS.toMillis(4)
        ));

        session.commit();
        session.close();
    }

};