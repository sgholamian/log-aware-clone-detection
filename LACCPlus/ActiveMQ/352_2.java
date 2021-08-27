//,temp,DeadLetterExpiryTest.java,101,143,temp,AMQ3405Test.java,91,146
//,3
public class xxx {
    protected void doTest() throws Exception {
        messageCount = 200;
        connection.start();

        final QueueViewMBean dlqView = getProxyToDLQ();

        ActiveMQConnection amqConnection = (ActiveMQConnection) connection;
        rollbackCount = amqConnection.getRedeliveryPolicy().getMaximumRedeliveries() + 1;
        LOG.info("Will redeliver messages: " + rollbackCount + " times");

        makeConsumer();
        makeDlqConsumer();
        dlqConsumer.close();

        sendMessages();

        // now lets receive and rollback N times
        int maxRollbacks = messageCount * rollbackCount;

        consumer.setMessageListener(new RollbackMessageListener(maxRollbacks, rollbackCount));

        // We receive and rollback into the DLQ N times moving the DLQ messages back to their
        // original Q to test that they are continually placed back in the DLQ.
        for (int i = 0; i < 2; ++i) {

            assertTrue("DLQ was not filled as expected", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return dlqView.getQueueSize() == messageCount;
                }
            }));

            connection.stop();

            assertEquals("DLQ should be full now.", messageCount, dlqView.getQueueSize());

            String moveTo;
            if (topic) {
                moveTo = "topic://" + ((Topic) getDestination()).getTopicName();
            } else {
                moveTo = "queue://" + ((Queue) getDestination()).getQueueName();
            }

            LOG.debug("Moving " + messageCount + " messages from ActiveMQ.DLQ to " + moveTo);
            dlqView.moveMatchingMessagesTo("", moveTo);

            assertTrue("DLQ was not emptied as expected", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return dlqView.getQueueSize() == 0;
                }
            }));

            connection.start();
        }
    }

};