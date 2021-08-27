//,temp,DiscriminatingConsumerLoadTest.java,149,197,temp,DiscriminatingConsumerLoadTest.java,100,142
//,3
public class xxx {
    public void testNonDiscriminatingConsumer() throws Exception {

        consumerConnection = createConnection();
        consumerConnection.start();
        LOG.info("consumerConnection = " + consumerConnection);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        // here we pass in null for the JMS selector
        Consumer consumer = new Consumer(consumerConnection, null);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();

        producerConnection = createConnection();
        producerConnection.start();
        LOG.info("producerConnection = " + producerConnection);

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }

        Producer producer = new Producer(producerConnection);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        // now that everything is running, let's wait for the consumer thread to finish ...
        consumerThread.join();
        producer.stop = true;

        if (consumer.getCount() == testSize)
            LOG.info("test complete .... all messsages consumed!!");
        else
            LOG.info("test failed .... Sent " + (testSize / 1) + " messages intended to be consumed ( " + testSize + " total), but only consumed "
                + consumer.getCount());

        assertTrue("Sent " + testSize + " messages intended to be consumed, but only consumed " + consumer.getCount(), (consumer.getCount() == testSize));
        assertFalse("Delivery of messages to consumer was halted during this test", consumer.deliveryHalted());
    }

};