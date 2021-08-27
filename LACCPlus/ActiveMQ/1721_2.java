//,temp,JmsXAQueueTransactionTest.java,175,221,temp,JmsTransactionTestSupport.java,535,570
//,3
public class xxx {
    public void testCloseConsumerBeforeCommit() throws Exception {
        TextMessage[] outbound = new TextMessage[] {session.createTextMessage("First Message"), session.createTextMessage("Second Message")};

        // lets consume any outstanding messages from prev test runs
        beginTx();
        while (consumer.receiveNoWait() != null) {
        }

        commitTx();

        // sends the messages
        beginTx();
        producer.send(outbound[0]);
        producer.send(outbound[1]);
        commitTx();
        LOG.info("Sent 0: " + outbound[0]);
        LOG.info("Sent 1: " + outbound[1]);

        beginTx();
        TextMessage message = (TextMessage)consumer.receive(1000);
        assertEquals(outbound[0].getText(), message.getText());
        // Close the consumer before the commit. This should not cause the
        // received message
        // to rollback.
        consumer.close();
        commitTx();

        // Create a new consumer
        consumer = resourceProvider.createConsumer(session, destination);
        LOG.info("Created consumer: " + consumer);

        beginTx();
        message = (TextMessage)consumer.receive(1000);
        assertEquals(outbound[1].getText(), message.getText());
        commitTx();
    }

};