//,temp,DestinationGCStressTest.java,156,248,temp,DestinationGCStressTest.java,90,154
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testClashWithPublishAndGC() throws Exception {

        org.apache.log4j.Logger log4jLogger =
                org.apache.log4j.Logger.getLogger(RegionBroker.class);
        final AtomicBoolean failed = new AtomicBoolean(false);

        Appender appender = new DefaultTestAppender() {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.ERROR) && event.getMessage().toString().startsWith("Failed to remove inactive")) {
                    logger.info("received unexpected log message: " + event.getMessage());
                    failed.set(true);
                }
            }
        };
        log4jLogger.addAppender(appender);
        try {

            final AtomicInteger max = new AtomicInteger(20000);

            final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?create=false");
            factory.setWatchTopicAdvisories(false);
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            final MessageConsumer messageConsumer = session.createConsumer(new ActiveMQTopic(">"));

            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < 1; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection c = factory.createConnection();
                            c.start();
                            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
                            MessageProducer producer = s.createProducer(null);
                            Message message = s.createTextMessage();
                            int j;
                            while ((j = max.decrementAndGet()) > 0) {
                                producer.send(new ActiveMQTopic("A." + j), message);
                            }
                            c.close();
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);

            logger.info("Done");

            connection.close();

        } finally {
            log4jLogger.removeAppender(appender);
        }
        assertFalse("failed on unexpected log event", failed.get());

    }

};