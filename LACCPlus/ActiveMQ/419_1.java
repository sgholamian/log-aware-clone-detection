//,temp,FailoverConsumerOutstandingCommitTest.java,200,304,temp,FailoverConsumerOutstandingCommitTest.java,103,188
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void doTestFailoverConsumerOutstandingSendTx(final boolean doActualBrokerCommit) throws Exception {
        final boolean watchTopicAdvisories = true;
        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[] { new BrokerPluginSupport() {
            @Override
            public void commitTransaction(ConnectionContext context,
                    TransactionId xid, boolean onePhase) throws Exception {
                // from the consumer perspective whether the commit completed on the broker or
                // not is irrelevant, the transaction is still in doubt in the absence of a reply
                if (doActualBrokerCommit) {
                    LOG.info("doing actual broker commit...");
                    super.commitTransaction(context, xid, onePhase);
                }
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
        } });
        broker.start();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        cf.setWatchTopicAdvisories(watchTopicAdvisories);
        cf.setDispatchAsync(false);

        final ActiveMQConnection connection = (ActiveMQConnection) cf.createConnection();
        connection.start();

        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = producerSession.createQueue(QUEUE_NAME
                + "?consumer.prefetchSize=" + prefetch);

        final Queue signalDestination = producerSession.createQueue(QUEUE_NAME + ".signal"
                + "?consumer.prefetchSize=" + prefetch);

        final Session consumerSession = connection.createSession(true, Session.SESSION_TRANSACTED);

        final CountDownLatch commitDoneLatch = new CountDownLatch(1);
        final CountDownLatch messagesReceived = new CountDownLatch(3);
        final AtomicBoolean gotCommitException = new AtomicBoolean(false);
        final ArrayList<TextMessage> receivedMessages = new ArrayList<TextMessage>();
        final MessageConsumer testConsumer = consumerSession.createConsumer(destination);
        testConsumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                LOG.info("consume one and commit: " + message);
                assertNotNull("got message", message);
                receivedMessages.add((TextMessage) message);
                try {
                    produceMessage(consumerSession, signalDestination, 1);
                    consumerSession.commit();
                } catch (JMSException e) {
                    LOG.info("commit exception", e);
                    gotCommitException.set(true);
                }
                commitDoneLatch.countDown();
                messagesReceived.countDown();
                LOG.info("done commit");
            }
        });

        // may block if broker shutdown happens quickly
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

        assertTrue("commit done through failover", commitDoneLatch.await(20, TimeUnit.SECONDS));
        assertTrue("commit failed", gotCommitException.get());
        assertTrue("another message was received after failover", messagesReceived.await(20, TimeUnit.SECONDS));
        int receivedIndex = 0;
        assertEquals("get message 0 first", MESSAGE_TEXT + "0", receivedMessages.get(receivedIndex++).getText());
        if (!doActualBrokerCommit) {
            // it will be redelivered and not tracked as a duplicate
            assertEquals("get message 0 second", MESSAGE_TEXT + "0", receivedMessages.get(receivedIndex++).getText());
        }
        assertTrue("another message was received", messagesReceived.await(20, TimeUnit.SECONDS));
        assertEquals("get message 1 eventually", MESSAGE_TEXT + "1", receivedMessages.get(receivedIndex++).getText());

        connection.close();
    }

};