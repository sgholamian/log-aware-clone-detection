//,temp,FailoverTransactionTest.java,725,871,temp,FailoverTransactionTest.java,177,269
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void testFailoverCommitReplyLost() throws Exception {

        broker = createBroker(true);
        setDefaultPersistenceAdapter(broker);

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void commitTransaction(ConnectionContext context,
                                                  TransactionId xid, boolean onePhase) throws Exception {
                        super.commitTransaction(context, xid, onePhase);
                        // so commit will hang as if reply is lost
                        context.setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker post commit...");
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
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(destination);
        produceMessage(session, destination);

        final CountDownLatch commitDoneLatch = new CountDownLatch(1);
        // broker will die on commit reply so this will hang till restart
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    session.commit();
                } catch (JMSException e) {
                    assertTrue(e instanceof TransactionRolledBackException);
                    LOG.info("got commit exception: ", e);
                }
                commitDoneLatch.countDown();
                LOG.info("done async commit");
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();
        broker = createBroker(false, url);
        setDefaultPersistenceAdapter(broker);
        broker.start();

        assertTrue("tx committed through failover", commitDoneLatch.await(30, TimeUnit.SECONDS));

        // new transaction
        Message msg = consumer.receive(20000);
        LOG.info("Received: " + msg);
        assertNotNull("we got the message", msg);
        assertNull("we got just one message", consumer.receive(2000));
        session.commit();
        consumer.close();
        connection.close();

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
        Session session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session2.createConsumer(destination);
        msg = consumer.receive(1000);
        LOG.info("Received: " + msg);
        assertNull("no messges left dangling but got: " + msg, msg);
        connection.close();
    }

};