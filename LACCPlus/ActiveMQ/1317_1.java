//,temp,JMXRemoveQueueThenSendIgnoredTest.java,99,121,temp,JMXRemoveQueueThenSendIgnoredTest.java,75,97
//,2
public class xxx {
    @Test
    public void testRemoveQueueAndProduceBeforeNewConsumerAdded() throws Exception {
        MessageConsumer firstConsumer = registerConsumer();
        produceMessage();
        Message message = firstConsumer.receive(5000);
        LOG.info("Received message " + message);

        assertEquals(1, numberOfMessages());
        firstConsumer.close();
        session.commit();
        Thread.sleep(1000);

        removeQueue();
        Thread.sleep(1000);

        produceMessage();
        MessageConsumer secondConsumer = registerConsumer();
        message = secondConsumer.receive(5000);
        LOG.debug("Received message " + message);

        assertEquals(1, numberOfMessages());
        secondConsumer.close();
    }

};