//,temp,FailoverConsumerUnconsumedTest.java,235,346,temp,FailoverConsumerUnconsumedTest.java,100,233
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void doTestFailoverConsumerDups(final boolean watchTopicAdvisories) throws Exception {

        final int maxConsumers = 4;
        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[] {
                new BrokerPluginSupport() {
                    int consumerCount;

                    // broker is killed on x create consumer
                    @Override
                    public Subscription addConsumer(ConnectionContext context,
                            final ConsumerInfo info) throws Exception {
                         if (++consumerCount == maxConsumers + (watchTopicAdvisories ? 1:0)) {
                             context.setDontSendReponse(true);
                             Executors.newSingleThreadExecutor().execute(new Runnable() {
                                 public void run() {
                                     LOG.info("Stopping broker on consumer: " + info.getConsumerId());
                                     try {
                                         broker.stop();
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }
                             });
                         }
                        return super.addConsumer(context, info);
                    }
                }
        });
        broker.start();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        cf.setWatchTopicAdvisories(watchTopicAdvisories);

        final ActiveMQConnection connection = (ActiveMQConnection) cf.createConnection();
        connection.start();

        final Session consumerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = consumerSession.createQueue(QUEUE_NAME + "?jms.consumer.prefetch=" + prefetch);

        final Vector<TestConsumer> testConsumers = new Vector<TestConsumer>();
        for (int i=0; i<maxConsumers -1; i++) {
            testConsumers.add(new TestConsumer(consumerSession, destination, connection));
        }

        produceMessage(consumerSession, destination, maxConsumers * prefetch);

        assertTrue("add messages are dispatched", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                int totalUnconsumed = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long unconsumed = testConsumer.unconsumedSize();
                    LOG.info(testConsumer.getConsumerId() + " unconsumed: " + unconsumed);
                    totalUnconsumed += unconsumed;
                }
                return totalUnconsumed == (maxConsumers-1) * prefetch;
            }
        }));

        final CountDownLatch shutdownConsumerAdded = new CountDownLatch(1);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    LOG.info("add last consumer...");
                    testConsumers.add(new TestConsumer(consumerSession, destination, connection));
                    shutdownConsumerAdded.countDown();
                    LOG.info("done add last consumer");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();

        // verify interrupt
        assertTrue("add messages dispatched and unconsumed are cleaned up", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                int totalUnconsumed = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long unconsumed = testConsumer.unconsumedSize();
                    LOG.info(testConsumer.getConsumerId() + " unconsumed: " + unconsumed);
                    totalUnconsumed += unconsumed;
                }
                return totalUnconsumed == 0;
            }
        }));

        broker = createBroker(false, this.url);
        broker.start();

        assertTrue("consumer added through failover", shutdownConsumerAdded.await(30, TimeUnit.SECONDS));

        // each should again get prefetch messages - all unconsumed deliveries should be rolledback
        assertTrue("after start all messages are re dispatched", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                int totalUnconsumed = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long unconsumed = testConsumer.unconsumedSize();
                    LOG.info(testConsumer.getConsumerId() + " after restart: unconsumed: " + unconsumed);
                    totalUnconsumed += unconsumed;
                }
                return totalUnconsumed == (maxConsumers) * prefetch;
            }
        }));

        connection.close();
    }

};