//,temp,FailoverConsumerUnconsumedTest.java,235,346,temp,FailoverConsumerUnconsumedTest.java,100,233
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test
    public void testFailoverClientAckMissingRedelivery() throws Exception {

        final int maxConsumers = 2;
        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[] {
                new BrokerPluginSupport() {
                    int consumerCount;

                    // broker is killed on x create consumer
                    @Override
                    public Subscription addConsumer(ConnectionContext context,
                            final ConsumerInfo info) throws Exception {
                         if (++consumerCount == maxConsumers) {
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
        cf.setWatchTopicAdvisories(false);

        final ActiveMQConnection connection = (ActiveMQConnection) cf.createConnection();
        connection.start();

        final Session consumerSession = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        final Queue destination = consumerSession.createQueue(QUEUE_NAME + "?jms.consumer.prefetch=" + prefetch);

        final Vector<TestConsumer> testConsumers = new Vector<TestConsumer>();
        TestConsumer testConsumer = new TestConsumer(consumerSession, destination, connection);
        testConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    LOG.info("onMessage:" + message.getJMSMessageID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        testConsumers.add(testConsumer);


        produceMessage(consumerSession, destination, maxConsumers * prefetch);

        assertTrue("add messages are delivered", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                int totalDelivered = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long delivered = testConsumer.deliveredSize();
                    LOG.info(testConsumer.getConsumerId() + " delivered: " + delivered);
                    totalDelivered += delivered;
                }
                return totalDelivered == maxConsumers * prefetch;
            }
        }));

        final CountDownLatch shutdownConsumerAdded = new CountDownLatch(1);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    LOG.info("add last consumer...");
                    TestConsumer testConsumer = new TestConsumer(consumerSession, destination, connection);
                    testConsumer.setMessageListener(new MessageListener() {
                                @Override
                                public void onMessage(Message message) {
                                    try {
                                        LOG.info("onMessage:" + message.getJMSMessageID());
                                    } catch (JMSException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    testConsumers.add(testConsumer);
                    shutdownConsumerAdded.countDown();
                    LOG.info("done add last consumer");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();

        broker = createBroker(false, this.url);
        broker.start();

        assertTrue("consumer added through failover", shutdownConsumerAdded.await(30, TimeUnit.SECONDS));

        // each should again get prefetch messages - all unacked deliveries should be rolledback
        assertTrue("after restart all messages are re dispatched", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                int totalDelivered = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long delivered = testConsumer.deliveredSize();
                    LOG.info(testConsumer.getConsumerId() + " delivered: " + delivered);
                    totalDelivered += delivered;
                }
                return totalDelivered == maxConsumers * prefetch;
            }
        }));

        assertTrue("after restart each got prefetch amount", Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                for (TestConsumer testConsumer : testConsumers) {
                    long delivered = testConsumer.deliveredSize();
                    LOG.info(testConsumer.getConsumerId() + " delivered: " + delivered);
                    if (delivered != prefetch) {
                        return false;
                    }
                }
                return true;
            }
        }));

        connection.close();
    }

};