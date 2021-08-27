//,temp,FailoverTransactionTest.java,725,871,temp,FailoverTransactionTest.java,177,269
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void doTestFailoverConsumerAckLost(final int pauseSeconds) throws Exception {
        broker = createBroker(true);
        setDefaultPersistenceAdapter(broker);

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {

                    // broker is killed on delivered ack as prefetch is 1
                    @Override
                    public void acknowledge(
                            ConsumerBrokerExchange consumerExchange,
                            final MessageAck ack) throws Exception {

                        consumerExchange.getConnectionContext().setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker on ack: " + ack);
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

        Vector<Connection> connections = new Vector<Connection>();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        connections.add(connection);
        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = producerSession.createQueue(QUEUE_NAME + "?consumer.prefetchSize=1");

        connection = cf.createConnection();
        connection.start();
        connections.add(connection);
        final Session consumerSession1 = connection.createSession(true, Session.SESSION_TRANSACTED);

        connection = cf.createConnection();
        connection.start();
        connections.add(connection);
        final Session consumerSession2 = connection.createSession(true, Session.SESSION_TRANSACTED);

        final MessageConsumer consumer1 = consumerSession1.createConsumer(destination);
        final MessageConsumer consumer2 = consumerSession2.createConsumer(destination);

        produceMessage(producerSession, destination);
        produceMessage(producerSession, destination);

        final Vector<Message> receivedMessages = new Vector<Message>();
        final CountDownLatch commitDoneLatch = new CountDownLatch(1);
        final AtomicBoolean gotTransactionRolledBackException = new AtomicBoolean(false);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async commit after consume...");
                try {
                    Message msg = consumer1.receive(20000);
                    LOG.info("consumer1 first attempt got message: " + msg);
                    receivedMessages.add(msg);

                    // give some variance to the runs
                    TimeUnit.SECONDS.sleep(random.nextInt(5));

                    // should not get a second message as there are two messages and two consumers
                    // and prefetch=1, but with failover and unordered connection restore it can get the second
                    // message.

                    // For the transaction to complete it needs to get the same one or two messages
                    // again so that the acks line up.
                    // If redelivery order is different, the commit should fail with an ex
                    //
                    msg = consumer1.receive(5000);
                    LOG.info("consumer1 second attempt got message: " + msg);
                    if (msg != null) {
                        receivedMessages.add(msg);
                    }

                    LOG.info("committing consumer1 session: " + receivedMessages.size() + " messsage(s)");
                    try {
                        consumerSession1.commit();
                    } catch (TransactionRolledBackException expected) {
                        LOG.info("got exception ex on commit", expected);
                        gotTransactionRolledBackException.set(true);
                        // ok, message one was not replayed so we expect the rollback
                    }
                    commitDoneLatch.countDown();
                    LOG.info("done async commit");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();
        broker = createBroker(false, url);
        setDefaultPersistenceAdapter(broker);
        broker.start();

        assertTrue("tx committed through failover", commitDoneLatch.await(30, TimeUnit.SECONDS));

        LOG.info("received message count: " + receivedMessages.size());

        // new transaction to get both messages from either consumer
        for (int i=0; i<2; i++) {
            Message msg = consumer1.receive(5000);
            LOG.info("post: from consumer1 received: " + msg);
            consumerSession1.commit();
            if (msg == null) {
                msg = consumer2.receive(10000);
                LOG.info("post: from consumer2 received: " + msg);
                consumerSession2.commit();
            }
            assertNotNull("got message [" + i + "]", msg);
        }

        for (Connection c : connections) {
            c.close();
        }

        // ensure no dangling messages with fresh broker etc
        broker.stop();
        broker.waitUntilStopped();

        LOG.info("Checking for remaining/hung messages..");
        broker = createBroker(false, url);
        setDefaultPersistenceAdapter(broker);
        broker.start();

        // after restart, ensure no dangling messages
        cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        configureConnectionFactory(cf);
        connection = cf.createConnection();
        connection.start();
        Session sweeperSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer sweeper = sweeperSession.createConsumer(destination);
        Message msg = sweeper.receive(1000);
        LOG.info("Sweep received: " + msg);
        assertNull("no messges left dangling but got: " + msg, msg);
        connection.close();
    }

};