//,temp,JmsXAQueueTransactionTest.java,175,221,temp,JmsTransactionTestSupport.java,535,570
//,3
public class xxx {
    public void testReceiveTwoThenAbort() throws Exception {
        Message[] outbound = new Message[] {session.createTextMessage("First Message"), session.createTextMessage("Second Message")};

        // lets consume any outstanding messages from prev test runs
        beginTx();
        while (consumer.receive(1000) != null) {
        }
        commitTx();

        //
        beginTx();
        producer.send(outbound[0]);
        producer.send(outbound[1]);
        commitTx();

        LOG.info("Sent 0: " + outbound[0]);
        LOG.info("Sent 1: " + outbound[1]);

        ArrayList<Message> messages = new ArrayList<Message>();
        beginTx();
        Message message = consumer.receive(1000);
        assertEquals(outbound[0], message);

        message = consumer.receive(1000);
        assertNotNull(message);
        assertEquals(outbound[1], message);
        abortTx();

        // Consume again.. the prev message should
        // get redelivered.
        beginTx();
        message = consumer.receive(5000);
        assertNotNull("Should have re-received the first message again!", message);
        messages.add(message);
        assertEquals(outbound[0], message);
        message = consumer.receive(5000);
        assertNotNull("Should have re-received the second message again!", message);
        messages.add(message);
        assertEquals(outbound[1], message);

        assertNull(consumer.receiveNoWait());
        commitTx();

        Message inbound[] = new Message[messages.size()];
        messages.toArray(inbound);
        assertTextMessagesEqual("Rollback did not work", outbound, inbound);
    }

};