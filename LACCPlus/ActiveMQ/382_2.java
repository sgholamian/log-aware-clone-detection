//,temp,ActiveMQXAConnectionTxInterruptTest.java,121,147,temp,ActiveMQXAConnectionTxInterruptTest.java,92,119
//,3
public class xxx {
    @Test
    public void testRollbackAckInterrupted() throws Exception {

        // publish a message
        publishAMessage();
        Xid tid;

        // consume in tx and rollback with interrupt
        session = xaConnection.createXASession();
        final MessageConsumer consumer = session.createConsumer(dest);
        tid = createXid();
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
        resource.end(tid, XAResource.TMFAIL);
        resource.rollback(tid);
        session.close();
        assertTrue("Was interrupted", Thread.currentThread().isInterrupted());
    }

};