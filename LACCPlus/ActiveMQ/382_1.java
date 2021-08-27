//,temp,ActiveMQXAConnectionTxInterruptTest.java,121,147,temp,ActiveMQXAConnectionTxInterruptTest.java,92,119
//,3
public class xxx {
    @Test
    public void testCommitAckInterrupted() throws Exception {

        // publish a message
        publishAMessage();

        // consume in tx and rollback with interrupt
        session = xaConnection.createXASession();
        MessageConsumer consumer = session.createConsumer(dest);
        Xid tid = createXid();
        resource = session.getXAResource();
        resource.start(tid, XAResource.TMNOFLAGS);
        ((TransactionContext)resource).addSynchronization(new Synchronization() {
            @Override
            public void beforeEnd() throws Exception {
                LOG.info("Interrupting thread: " + Thread.currentThread(), new Throwable("Source"));
                Thread.currentThread().interrupt();
            }
        });
        TextMessage receivedMessage = (TextMessage) consumer.receive(1000);
        assertNotNull(receivedMessage);
        assertEquals(getName(), receivedMessage.getText());
        resource.end(tid, XAResource.TMSUCCESS);
        resource.commit(tid, true);
        session.close();

    }

};