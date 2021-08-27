//,temp,JmsTransactionTestSupport.java,270,308,temp,JmsTransactionTestSupport.java,195,232
//,3
public class xxx {
    public void testSendRollback() throws Exception {
        Message[] outbound = new Message[] {session.createTextMessage("First Message"), session.createTextMessage("Second Message")};

        // sends a message
        beginTx();
        producer.send(outbound[0]);
        commitTx();

        // sends a message that gets rolled-back
        beginTx();
        producer.send(session.createTextMessage("I'm going to get rolled back."));
        rollbackTx();

        // sends a message
        beginTx();
        producer.send(outbound[1]);
        commitTx();

        // receives the first message
        beginTx();
        ArrayList<Message> messages = new ArrayList<Message>();
        LOG.info("About to consume message 1");
        Message message = consumer.receive(1000);
        messages.add(message);
        LOG.info("Received: " + message);

        // receives the second message
        LOG.info("About to consume message 2");
        message = consumer.receive(4000);
        messages.add(message);
        LOG.info("Received: " + message);

        // validates that the rolled-back was not consumed
        commitTx();
        Message inbound[] = new Message[messages.size()];
        messages.toArray(inbound);
        assertTextMessagesEqual("Rollback did not work.", outbound, inbound);
    }

};