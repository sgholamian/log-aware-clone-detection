//,temp,FailoverConsumerOutstandingCommitTest.java,200,304,temp,FailoverConsumerOutstandingCommitTest.java,103,188
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void doTestFailoverConsumerDups(final boolean watchTopicAdvisories) throws Exception {

        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[] {
                new BrokerPluginSupport() {
                    @Override
                    public void commitTransaction(ConnectionContext context,
                            TransactionId xid, boolean onePhase) throws Exception {
                        // so commit will hang as if reply is lost
                        context.setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker before commit...");
                                try {
                                    broker.stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
        });
        broker.start();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        cf.setWatchTopicAdvisories(watchTopicAdvisories);
        cf.setDispatchAsync(false);

        final ActiveMQConnection connection = (ActiveMQConnection) cf.createConnection();
        connection.start();

        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = producerSession.createQueue(QUEUE_NAME + "?consumer.prefetchSize=" + prefetch);

        final Session consumerSession = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        final CountDownLatch commitDoneLatch = new CountDownLatch(1);
        final CountDownLatch messagesReceived = new CountDownLatch(2);

        final MessageConsumer testConsumer = consumerSession.createConsumer(destination);
        testConsumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                LOG.info("consume one and commit");

                assertNotNull("got message", message);

                try {
                    consumerSession.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                commitDoneLatch.countDown();
                messagesReceived.countDown();
                LOG.info("done commit");
            }
        });

        // may block if broker shutodwn happens quickly
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("producer started");
                try {
                    produceMessage(producerSession, destination, prefetch * 2);
                } catch (javax.jms.IllegalStateException SessionClosedExpectedOnShutdown) {
                } catch (JMSException e) {
                    e.printStackTrace();
                    fail("unexpceted ex on producer: " + e);
                }
                LOG.info("producer done");
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();
        broker = createBroker(false, url);
        broker.start();

        assertTrue("consumer added through failover", commitDoneLatch.await(20, TimeUnit.SECONDS));
        assertTrue("another message was recieved after failover", messagesReceived.await(20, TimeUnit.SECONDS));

        connection.close();
    }

};