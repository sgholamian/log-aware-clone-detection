//,temp,DiscriminatingConsumerLoadTest.java,149,197,temp,DiscriminatingConsumerLoadTest.java,100,142
//,3
public class xxx {
    public void testDiscriminatingConsumer() throws Exception {

        consumerConnection = createConnection();
        consumerConnection.start();
        LOG.info("consumerConnection = " + consumerConnection);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        // here we pass the JMS selector we intend to consume
        Consumer consumer = new Consumer(consumerConnection, JMSTYPE_EATME);
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

        if (consumer.getCount() == (testSize / 2)) {
            LOG.info("test complete .... all messsages consumed!!");
        } else {
            LOG.info("test failed .... Sent " + testSize + " original messages, only half of which (" + (testSize / 2)
                + ") were intended to be consumed: consumer paused at: " + consumer.getCount());
            // System.out.println("test failed .... Sent " + testSize + " original messages, only half of which (" +
            // (testSize / 2) +
            // ") were intended to be consumed: consumer paused at: " + consumer.getCount());

        }

        assertTrue("Sent " + testSize + " original messages, only half of which (" + (testSize / 2) + ") were intended to be consumed: consumer paused at: "
            + consumer.getCount(), (consumer.getCount() == (testSize / 2)));
        assertTrue("Delivery of messages to consumer was halted during this test as it only wants half", consumer.deliveryHalted());
    }

};