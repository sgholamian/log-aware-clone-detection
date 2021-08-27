//,temp,DiscardingDeadLetterPolicyTest.java,53,76,temp,DeadLetterTest.java,39,63
//,3
public class xxx {
    protected void doTest() throws Exception {
        connection.start();

        ActiveMQConnection amqConnection = (ActiveMQConnection) connection;
        rollbackCount = amqConnection.getRedeliveryPolicy().getMaximumRedeliveries() + 1;
        LOG.info("Will redeliver messages: " + rollbackCount + " times");

        makeConsumer();
        makeDlqConsumer();

        sendMessages();

        // now lets receive and rollback N times
        for (int i = 0; i < messageCount; i++) {
            consumeAndRollback(i);
        }

        verifyIsDlq((Queue) dlqDestination);
        for (int i = 0; i < messageCount; i++) {
            Message msg = dlqConsumer.receive(1000);
            assertMessage(msg, i);
            assertNotNull("Should be a DLQ message for loop: " + i, msg);
        }
        session.commit();
    }

};