//,temp,LargeQueueSparseDeleteTest.java,106,151,temp,LargeQueueSparseDeleteTest.java,58,104
//,2
public class xxx {
    public void testMoveMessages() throws Exception {
        final int QUEUE_SIZE = 30000;
        final String MOVE_TO_DESTINATION_NAME = getDestinationString()
                + ".dest";
        final long TEST_TIMEOUT = 20000;

        // Populate a test queue with uniquely-identifiable messages.
        Connection conn = createConnection();
        try {
            conn.start();
            Session session = conn.createSession(true,
                    Session.SESSION_TRANSACTED);
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < QUEUE_SIZE; i++) {
                Message message = session.createMessage();
                message.setIntProperty("id", i);
                producer.send(message);
            }
            session.commit();
        } finally {
            conn.close();
        }

        // Access the implementation of the test queue and move the last message
        // to another queue. Verify that the move occurred within the limits of
        // the test.
        Queue queue = (Queue) broker.getRegionBroker().getDestinationMap().get(
                destination);

        ConnectionContext context = new ConnectionContext();
        context.setBroker(broker.getBroker());
        context.getMessageEvaluationContext().setDestination(destination);

        long startTimeMillis = System.currentTimeMillis();
        Assert.assertEquals(1, queue
                .moveMatchingMessagesTo(context, "id=" + (QUEUE_SIZE - 1),
                        createDestination(MOVE_TO_DESTINATION_NAME)));

        long durationMillis = System.currentTimeMillis() - startTimeMillis;

        LOG.info("It took " + durationMillis
                + "ms to move the last message from a queue a " + QUEUE_SIZE
                + " messages.");

        Assert.assertTrue("Moving the message took too long: " + durationMillis
                + "ms", durationMillis < TEST_TIMEOUT);
    }

};