//,temp,JMSClientTransactionTest.java,207,292,temp,JMSClientTransactionTest.java,160,205
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testQueueTXRollbackAndCommitAsyncConsumer() throws Exception {
        final int MSG_COUNT = 3;

        final AtomicInteger counter = new AtomicInteger();

        connection = createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Queue destination = session.createQueue(getDestinationName());

        MessageProducer producer = session.createProducer(destination);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                try {
                    LOG.info("Received Message {}", message.getJMSMessageID());
                } catch (JMSException e) {
                }
                counter.incrementAndGet();
            }
        });

        int msgIndex = 0;
        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Sending message: {} to rollback", msgIndex++);
            TextMessage message = session.createTextMessage("Rolled back Message: " + msgIndex);
            message.setIntProperty("MessageSequence", msgIndex);
            producer.send(message);
        }

        LOG.info("ROLLBACK of sent message here:");
        session.rollback();

        assertEquals(0, getProxyToQueue(getDestinationName()).getQueueSize());

        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Sending message: {} to commit", msgIndex++);
            TextMessage message = session.createTextMessage("Commit Message: " + msgIndex);
            message.setIntProperty("MessageSequence", msgIndex);
            producer.send(message);
        }

        LOG.info("COMMIT of sent message here:");
        session.commit();

        assertEquals(MSG_COUNT, getProxyToQueue(getDestinationName()).getQueueSize());
        SubscriptionViewMBean subscription = getProxyToQueueSubscriber(getDestinationName());
        assertNotNull(subscription);

        assertTrue("Should read all " + MSG_COUNT + " messages.", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return counter.get() == MSG_COUNT;
            }
        }));

        LOG.info("COMMIT of first received batch here:");
        session.commit();

        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Sending message: {} to commit", msgIndex++);
            TextMessage message = session.createTextMessage("Commit Message: " + msgIndex);
            message.setIntProperty("MessageSequence", msgIndex);
            producer.send(message);
        }

        LOG.info("COMMIT of next sent message batch here:");
        session.commit();

        LOG.info("WAITING -> for next three messages to arrive:");

        assertTrue("Should read all " + MSG_COUNT + " messages.", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Read {} messages so far", counter.get());
                return counter.get() == MSG_COUNT * 2;
            }
        }));
    }

};