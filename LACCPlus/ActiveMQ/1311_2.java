//,temp,DeadLetterExpiryTest.java,204,213,temp,DeadLetterTest.java,65,74
//,2
public class xxx {
    protected void consumeAndRollback(int messageCounter) throws Exception {
        for (int i = 0; i < rollbackCount; i++) {
            Message message = consumer.receive(5000);
            assertNotNull("No message received for message: " + messageCounter + " and rollback loop: " + i, message);
            assertMessage(message, messageCounter);

            session.rollback();
        }
        LOG.info("Rolled back: " + rollbackCount + " times");
    }

};