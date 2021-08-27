//,temp,NetworkBridgeProducerFlowControlTest.java,392,503,temp,NetworkBridgeProducerFlowControlTest.java,287,390
//,3
public class xxx {
    public void testSendFailIfNoSpaceReverseDoesNotBlockQueueNetwork() throws Exception {
        final int NUM_MESSAGES = 100;
        final long TEST_MESSAGE_SIZE = 1024;
        final long SLOW_CONSUMER_DELAY_MILLIS = 100;

        final ActiveMQQueue slowDestination = new ActiveMQQueue(
            NetworkBridgeProducerFlowControlTest.class.getSimpleName()
                    + ".slow.shared?consumer.prefetchSize=1");

        final ActiveMQQueue fastDestination = new ActiveMQQueue(
            NetworkBridgeProducerFlowControlTest.class.getSimpleName()
                    + ".fast.shared?consumer.prefetchSize=1");


        // Start a local and a remote broker.
        BrokerService localBroker = createBroker(new URI("broker:(tcp://localhost:0"
                + ")?brokerName=broker0&persistent=false&useJmx=true"));
        createBroker(new URI(
                "broker:(tcp://localhost:0"
                        + ")?brokerName=broker1&persistent=false&useJmx=true"));
        localBroker.getSystemUsage().setSendFailIfNoSpace(true);

        // Set a policy on the local broker that limits the maximum size of the
        // slow shared queue.
        PolicyEntry policyEntry = new PolicyEntry();
        policyEntry.setMemoryLimit(5 * TEST_MESSAGE_SIZE);
        PolicyMap policyMap = new PolicyMap();
        policyMap.put(slowDestination, policyEntry);
        localBroker.setDestinationPolicy(policyMap);

        // Create an outbound bridge from the local broker to the remote broker.
        // The bridge is configured with the remoteDispatchType enhancement.
        NetworkConnector nc = bridgeBrokers("broker0", "broker1");
        nc.setAlwaysSyncSend(true);
        nc.setPrefetchSize(1);
        nc.setDuplex(true);

        startAllBrokers();
        waitForBridgeFormation();

        // Start two asynchronous consumers on the local broker, one for each
        // of the two shared queues, and keep track of how long it takes for
        // each of the consumers to receive all the messages.
        final CountDownLatch fastConsumerLatch = new CountDownLatch(
                NUM_MESSAGES);
        final CountDownLatch slowConsumerLatch = new CountDownLatch(
                NUM_MESSAGES);

        final long startTimeMillis = System.currentTimeMillis();
        final AtomicLong fastConsumerTime = new AtomicLong();
        final AtomicLong slowConsumerTime = new AtomicLong();

        Thread fastWaitThread = new Thread() {
            @Override
            public void run() {
                try {
                    fastConsumerLatch.await();
                    fastConsumerTime.set(System.currentTimeMillis()
                            - startTimeMillis);
                } catch (InterruptedException ex) {
                    exceptions.add(ex);
                    Assert.fail(ex.getMessage());
                }
            }
        };

        Thread slowWaitThread = new Thread() {
            @Override
            public void run() {
                try {
                    slowConsumerLatch.await();
                    slowConsumerTime.set(System.currentTimeMillis()
                            - startTimeMillis);
                } catch (InterruptedException ex) {
                    exceptions.add(ex);
                    Assert.fail(ex.getMessage());
                }
            }
        };

        fastWaitThread.start();
        slowWaitThread.start();

        createConsumer("broker0", fastDestination, fastConsumerLatch);
        MessageConsumer slowConsumer = createConsumer("broker0",
                slowDestination, slowConsumerLatch);
        MessageIdList messageIdList = brokers.get("broker0").consumers
                .get(slowConsumer);
        messageIdList.setProcessingDelay(SLOW_CONSUMER_DELAY_MILLIS);

        // Send the test messages to the local broker's shared queues. The
        // messages are either persistent or non-persistent to demonstrate the
        // difference between synchronous and asynchronous dispatch.
        persistentDelivery = false;
        sendMessages("broker1", fastDestination, NUM_MESSAGES);
        sendMessages("broker1", slowDestination, NUM_MESSAGES);

        fastWaitThread.join(TimeUnit.SECONDS.toMillis(60));
        slowWaitThread.join(TimeUnit.SECONDS.toMillis(60));

        assertTrue("no exceptions on the wait threads:" + exceptions,
                exceptions.isEmpty());

        LOG.info("Fast consumer duration (ms): " + fastConsumerTime.get());
        LOG.info("Slow consumer duration (ms): " + slowConsumerTime.get());

        assertTrue("fast time set", fastConsumerTime.get() > 0);
        assertTrue("slow time set", slowConsumerTime.get() > 0);

        // Verify the behaviour as described in the description of this class.
        Assert.assertTrue(fastConsumerTime.get() < slowConsumerTime.get() / 10);
    }

};