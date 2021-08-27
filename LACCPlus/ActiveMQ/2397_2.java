//,temp,MessageListenerRedeliveryTest.java,358,419,temp,MessageListenerRedeliveryTest.java,300,356
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testQueueSessionListenerExceptionDlq() throws Exception {
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue-" + getTestName());
        MessageProducer producer = createProducer(session, queue);
        Message message = createTextMessage(session);
        producer.send(message);

        final Message[] dlqMessage = new Message[1];
        ActiveMQDestination dlqDestination = new ActiveMQQueue("ActiveMQ.DLQ");
        MessageConsumer dlqConsumer = session.createConsumer(dlqDestination);
        final CountDownLatch gotDlqMessage = new CountDownLatch(1);
        dlqConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                LOG.info("DLQ Message Received: " + message);
                dlqMessage[0] = message;
                gotDlqMessage.countDown();
            }
        });

        MessageConsumer consumer = session.createConsumer(queue);

        final int maxDeliveries = getRedeliveryPolicy().getMaximumRedeliveries();
        final CountDownLatch gotMessage = new CountDownLatch(maxDeliveries);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                LOG.info("Message Received: " + message);
                gotMessage.countDown();
                throw new RuntimeException(getTestName() + " force a redelivery");
            }
        });

        assertTrue("got message before retry expiry", gotMessage.await(20, TimeUnit.SECONDS));

        // check DLQ
        assertTrue("got dlq message", gotDlqMessage.await(20, TimeUnit.SECONDS));

        // check DLQ message cause is captured
        message = dlqMessage[0];
        assertNotNull("dlq message captured", message);
        String cause = message.getStringProperty(ActiveMQMessage.DLQ_DELIVERY_FAILURE_CAUSE_PROPERTY);

        LOG.info("DLQ'd message cause reported as: {}", cause);

        assertTrue("cause 'cause' exception is remembered", cause.contains("RuntimeException"));
        assertTrue("is correct exception", cause.contains(getTestName()));
        assertTrue("cause exception is remembered", cause.contains("Throwable"));
        assertTrue("cause policy is remembered", cause.contains("RedeliveryPolicy"));
        assertTrue("cause redelivered count is remembered", cause.contains("[" + (maxDeliveries+1) +"]"));

        session.close();
    }

};