//,temp,JmsTransactionTestSupport.java,415,461,temp,JmsTransactionTestSupport.java,364,407
//,3
public class xxx {
    public void testReceiveRollback() throws Exception {
        Message[] outbound = new Message[] {session.createTextMessage("First Message"), session.createTextMessage("Second Message")};

        // lets consume any outstanding messages from prev test runs
        beginTx();
            while (consumer.receive(1000) != null) {
        }
        commitTx();

        // sent both messages
        beginTx();
        producer.send(outbound[0]);
        producer.send(outbound[1]);
        commitTx();

        LOG.info("Sent 0: " + outbound[0]);
        LOG.info("Sent 1: " + outbound[1]);

        ArrayList<Message> messages = new ArrayList<Message>();
        beginTx();
        Message message = consumer.receive(1000);
        messages.add(message);
        assertEquals(outbound[0], message);
        commitTx();

        // rollback so we can get that last message again.
        beginTx();
        message = consumer.receive(1000);
        assertNotNull(message);
        assertEquals(outbound[1], message);
        rollbackTx();

        // Consume again.. the prev message should
        // get redelivered.
        beginTx();
        message = consumer.receive(5000);
        assertNotNull("Should have re-received the message again!", message);
        messages.add(message);
        commitTx();

        Message inbound[] = new Message[messages.size()];
        messages.toArray(inbound);
        assertTextMessagesEqual("Rollback did not work", outbound, inbound);
    }

};