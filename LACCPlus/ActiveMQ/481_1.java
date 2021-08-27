//,temp,ConcurrentProducerQueueConsumerTest.java,82,163,temp,ConcurrentProducerDurableConsumerTest.java,92,164
//,3
public class xxx {
    public void testSendRateWithActivatingConsumers() throws Exception {
        final Destination destination = createDestination();
        final ConnectionFactory factory = createConnectionFactory();

        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = createMessageProducer(session, destination);

        // preload the queue before adding any consumers
        double[] noConsumerStats = produceMessages(destination, NUM_MESSAGES, ITERATIONS, session, producer, null);
        LOG.info("With no consumers: ave: " + noConsumerStats[1] + ", max: " +
                 noConsumerStats[0] + ", multiplier: " + (noConsumerStats[0]/noConsumerStats[1]));
        expectedQueueDeliveries = NUM_MESSAGES * ITERATIONS;

        // periodically start a queue consumer
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
                        consumer = createConsumer(factory.createConnection(), destination);
                        LOG.info("Created consumer " + consumer);
                        consumer.setMessageListener(listener);
                        consumers.put(consumer, listener);
                    }
                } catch (Exception e) {
                    LOG.error("failed to start consumer", e);
                }
            }
        });

        // Collect statistics when there are active consumers.
        double[] statsWithActive =
            produceMessages(destination, NUM_MESSAGES, ITERATIONS, session, producer, addConsumerSignal);
        expectedQueueDeliveries += NUM_MESSAGES * ITERATIONS;

        LOG.info(" with concurrent activate, ave: " + statsWithActive[1] + ", max: " +
                 statsWithActive[0] + ", multiplier: " + (statsWithActive[0]/ statsWithActive[1]));

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return consumers.size() == consumersToActivate;
            }
        }));

        long timeToFirstAccumulator = 0;
        for (TimedMessageListener listener : consumers.values()) {
            long time = listener.getFirstReceipt();
            timeToFirstAccumulator += time;
            LOG.info("Time to first " + time);
        }
        LOG.info("Ave time to first message =" + timeToFirstAccumulator/consumers.size());

        for (TimedMessageListener listener : consumers.values()) {
            LOG.info("Ave batch receipt time: " + listener.waitForReceivedLimit(expectedQueueDeliveries) +
                     " max receipt: " + listener.maxReceiptTime);
        }

        // compare no active to active
        LOG.info("Ave send time with active: " + statsWithActive[1]
                + " as multiplier of ave with none active: " + noConsumerStats[1]
                + ", multiplier=" + (statsWithActive[1]/noConsumerStats[1]));

        assertTrue("Ave send time with active: " + statsWithActive[1]
                + " within reasonable multpler of ave with none active: " + noConsumerStats[1]
                + ", multiplier " + (statsWithActive[1]/noConsumerStats[1]),
                statsWithActive[1] < 15 * noConsumerStats[1]);
    }

};