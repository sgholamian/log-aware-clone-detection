//,temp,DeadLetterExpiryTest.java,101,143,temp,AMQ3405Test.java,91,146
//,3
public class xxx {
    protected void doTest() throws Exception {
        connection.start();
        messageCount = 4;

        ActiveMQConnection amqConnection = (ActiveMQConnection) connection;
        rollbackCount = amqConnection.getRedeliveryPolicy().getMaximumRedeliveries() + 1;
        LOG.info("Will redeliver messages: " + rollbackCount + " times");

        makeConsumer();
        sendMessages();

        // now lets receive and rollback N times
        for (int i = 0; i < messageCount; i++) {
            consumeAndRollback(i);
        }

        Queue dlqQueue = (Queue) createDlqDestination();
        verifyIsDlq(dlqQueue);

        // they should expire
        final QueueViewMBean queueViewMBean = getProxyToQueue(dlqQueue.getQueueName());

        assertTrue("all dlq messages expired", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Queue size:" + queueViewMBean.getQueueSize());
                return queueViewMBean.getExpiredCount() == messageCount;
            }
        }));

        makeDlqConsumer();
        assertNull("no message available", dlqConsumer.receive(1000));

        final QueueViewMBean sharedDlqViewMBean = getProxyToQueue(SharedDeadLetterStrategy.DEFAULT_DEAD_LETTER_QUEUE_NAME);
        assertTrue("messages stay on shared dlq which has default expiration=0", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Q " + sharedDlqViewMBean.getName() + " size:" + sharedDlqViewMBean.getQueueSize());
                return sharedDlqViewMBean.getQueueSize() == messageCount;
            }
        }));

    }

};