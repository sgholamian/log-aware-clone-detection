//,temp,TopicProducerFlowControlTest.java,105,209,temp,TopicProducerToSubFileCursorTest.java,94,174
//,3
public class xxx {
    public void testTopicProducerFlowControl() throws Exception {

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

        listenerSession.createConsumer(destination).setMessageListener(new TopicProducerFlowControlTest());
        final AtomicInteger blockedCounter = new AtomicInteger(0);
        listenerSession.createConsumer(new ActiveMQTopic(AdvisorySupport.FULL_TOPIC_PREFIX + ">")).setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (blockedCounter.get() % 100 == 0) {
                        LOG.info("Got full advisory, usageName: " +
                                message.getStringProperty(AdvisorySupport.MSG_PROPERTY_USAGE_NAME) +
                                ", usageCount: " +
                                message.getLongProperty(AdvisorySupport.MSG_PROPERTY_USAGE_COUNT)
                                + ", blockedCounter: " + blockedCounter.get());
                    }
                    blockedCounter.incrementAndGet();

                } catch (Exception error) {
                    error.printStackTrace();
                    LOG.error("missing advisory property", error);
                }
            }
        });

        final AtomicInteger warnings = new AtomicInteger();
        Appender appender = new DefaultTestAppender() {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.WARN) && event.getMessage().toString().contains("Usage Manager memory limit reached")) {
                    LOG.info("received  log message: " + event.getMessage());
                    warnings.incrementAndGet();
                }
            }
        };
        org.apache.log4j.Logger log4jLogger =
                org.apache.log4j.Logger.getLogger(Topic.class);
        log4jLogger.addAppender(appender);
        try {

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

            assertTrue("Producer got blocked", Wait.waitFor(new Wait.Condition() {
                public boolean isSatisified() throws Exception {
                    return blockedCounter.get() > 0;
                }
            }, 5 * 1000));

            LOG.info("BlockedCount: " + blockedCounter.get() + ", Warnings:" + warnings.get());
            assertTrue("got a few warnings", warnings.get() > 1);
            assertTrue("warning limited", warnings.get() < blockedCounter.get());

        } finally {
            log4jLogger.removeAppender(appender);
        }
    }

};