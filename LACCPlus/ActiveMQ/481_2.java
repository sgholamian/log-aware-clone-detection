//,temp,ConcurrentProducerQueueConsumerTest.java,82,163,temp,ConcurrentProducerDurableConsumerTest.java,92,164
//,3
public class xxx {
    @Test(timeout = 120000)
    public void testSendRateWithActivatingConsumers() throws Exception {
        final Destination destination = createDestination();
        final ConnectionFactory factory = createConnectionFactory();
        startInactiveConsumers(factory, destination);

        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = createMessageProducer(session, destination);

        // preload the durable consumers
        double[] inactiveConsumerStats = produceMessages(destination, 500, 10, session, producer, null);
        LOG.info("With inactive consumers: ave: " + inactiveConsumerStats[1] + ", max: " + inactiveConsumerStats[0] + ", multiplier: "
            + (inactiveConsumerStats[0] / inactiveConsumerStats[1]));

        // periodically start a durable sub that has a backlog
        final int consumersToActivate = 5;
        final Object addConsumerSignal = new Object();
        Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "ActivateConsumer" + this);
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageConsumer consumer = null;
                    for (int i = 0; i < consumersToActivate; i++) {
                        LOG.info("Waiting for add signal from producer...");
                        synchronized (addConsumerSignal) {
                            addConsumerSignal.wait(30 * 60 * 1000);
                        }
                        TimedMessageListener listener = new TimedMessageListener();
                        consumer = createDurableSubscriber(factory.createConnection(), destination, "consumer" + (i + 1));
                        LOG.info("Created consumer " + consumer);
                        consumer.setMessageListener(listener);
                        consumers.put(consumer, listener);
                    }
                } catch (Exception e) {
                    LOG.error("failed to start consumer", e);
                }
            }
        });

        double[] statsWithActive = produceMessages(destination, 500, 10, session, producer, addConsumerSignal);

        LOG.info(" with concurrent activate, ave: " + statsWithActive[1] + ", max: " + statsWithActive[0] + ", multiplier: "
            + (statsWithActive[0] / statsWithActive[1]));

        while (consumers.size() < consumersToActivate) {
            TimeUnit.SECONDS.sleep(2);
        }

        long timeToFirstAccumulator = 0;
        for (TimedMessageListener listener : consumers.values()) {
            long time = listener.getFirstReceipt();
            timeToFirstAccumulator += time;
            LOG.info("Time to first " + time);
        }
        LOG.info("Ave time to first message =" + timeToFirstAccumulator / consumers.size());

        for (TimedMessageListener listener : consumers.values()) {
            LOG.info("Ave batch receipt time: " + listener.waitForReceivedLimit(10000) + " max receipt: " + listener.maxReceiptTime);
        }

        // compare no active to active
        LOG.info("Ave send time with active: " + statsWithActive[1] + " as multiplier of ave with none active: " + inactiveConsumerStats[1] + ", multiplier="
            + (statsWithActive[1] / inactiveConsumerStats[1]));

        assertTrue("Ave send time with active: " + statsWithActive[1] + " within reasonable multpler of ave with none active: " + inactiveConsumerStats[1]
            + ", multiplier " + (statsWithActive[1] / inactiveConsumerStats[1]), statsWithActive[1] < 15 * inactiveConsumerStats[1]);
    }

};