//,temp,TopicProducerFlowControlTest.java,105,209,temp,TopicProducerToSubFileCursorTest.java,94,174
//,3
public class xxx {
    public void testTopicProducerFlowControlNotUsedWhenSubSpoolsToDiskOnTwoPercentSystemUsage() throws Exception {

        // Create the connection factory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setAlwaysSyncSend(true);
        connectionFactory.setProducerWindowSize(1024);

        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setAll(5000);
        connectionFactory.setPrefetchPolicy(prefetchPolicy);
        // Start the test destination listener
        Connection c = connectionFactory.createConnection();
        c.start();
        Session listenerSession = c.createSession(false, 1);
        Destination destination = createDestination(listenerSession);

        listenerSession.createConsumer(destination).setMessageListener(new TopicProducerToSubFileCursorTest());
        final AtomicInteger blockedCounter = new AtomicInteger(0);
        listenerSession.createConsumer(new ActiveMQTopic(AdvisorySupport.FULL_TOPIC_PREFIX + ">")).setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    LOG.error("Got full advisory, usageName: " +
                            message.getStringProperty(AdvisorySupport.MSG_PROPERTY_USAGE_NAME) +
                            ", usageCount: " +
                            message.getLongProperty(AdvisorySupport.MSG_PROPERTY_USAGE_COUNT)
                            + ", blockedCounter: " + blockedCounter.get());

                    blockedCounter.incrementAndGet();

                } catch (Exception error) {
                    error.printStackTrace();
                    LOG.error("missing advisory property", error);
                }
            }
        });

        // Start producing the test messages
        final Session session = connectionFactory.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer producer = session.createProducer(destination);

        Thread producingThread = new Thread("Producing Thread") {
            public void run() {
                try {
                    for (long i = 0; i < numMessagesToSend; i++) {
                        producer.send(session.createTextMessage("test"));

                        long count = produced.incrementAndGet();
                        if (count % 10000 == 0) {
                            LOG.info("Produced " + count + " messages");
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        producer.close();
                        session.close();
                    } catch (Exception e) {
                    }
                }
            }
        };

        producingThread.start();

        Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                return consumed.get() == numMessagesToSend;
            }
        }, 5 * 60 * 1000); // give it plenty of time before failing

        assertEquals("Didn't produce all messages", numMessagesToSend, produced.get());
        assertEquals("Didn't consume all messages", numMessagesToSend, consumed.get());

         assertTrue("Producer did not get blocked", Wait.waitFor(new Wait.Condition() {
             public boolean isSatisified() throws Exception {
                 return blockedCounter.get() == 0;
             }
         }, 5 * 1000));
    }

};