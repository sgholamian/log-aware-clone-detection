//,temp,FailoverDuplicateTest.java,96,240,temp,FailoverTransactionTest.java,488,598
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void testFailoverConnectionSendReplyLost() throws Exception {

        broker = createBroker(true);
        PersistenceAdapter store = setDefaultPersistenceAdapter(broker);
        if (store instanceof KahaDBPersistenceAdapter) {
            // duplicate checker not updated on canceled tasks, even it
            // it was, recovery of the audit would fail as the message is
            // not recorded in the store and the audit may not be up to date.
            // So if duplicate messages are a absolute no no after restarts,
            // ConcurrentStoreAndDispatchQueues must be disabled
            ((KahaDBPersistenceAdapter) store).setConcurrentStoreAndDispatchQueues(false);
        }

        final SocketProxy proxy = new SocketProxy();

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    private boolean firstSend = true;

                    @Override
                    public void send(ProducerBrokerExchange producerExchange,
                                     org.apache.activemq.command.Message messageSend)
                            throws Exception {
                        // so send will hang as if reply is lost
                        super.send(producerExchange, messageSend);
                        if (firstSend) {
                            firstSend = false;

                            producerExchange.getConnectionContext().setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    LOG.info("Stopping connection post send...");
                                    try {
                                        proxy.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
        });
        broker.start();

        proxy.setTarget(new URI(url));
        proxy.open();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + proxy.getUrl().toASCIIString() + ")?jms.watchTopicAdvisories=false");
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(destination);
        final CountDownLatch sendDoneLatch = new CountDownLatch(1);
        // proxy connection will die on send reply so this will hang on failover reconnect till open
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async send...");
                try {
                    produceMessage(session, destination);
                } catch (JMSException e) {
                    //assertTrue(e instanceof TransactionRolledBackException);
                    LOG.info("got send exception: ", e);
                }
                sendDoneLatch.countDown();
                LOG.info("done async send");
            }
        });

        // will be closed by the plugin
        assertTrue("proxy was closed", proxy.waitUntilClosed(30));
        LOG.info("restarting proxy");
        proxy.open();

        assertTrue("message sent through failover", sendDoneLatch.await(30, TimeUnit.SECONDS));

        Message msg = consumer.receive(20000);
        LOG.info("Received: " + msg);
        assertNotNull("we got the message", msg);
        assertNull("we got just one message", consumer.receive(2000));
        consumer.close();
        connection.close();

        // verify stats, connection dup suppression means dups don't get to broker
        assertEquals("one queued message", 1, ((RegionBroker) broker.getRegionBroker()).getDestinationStatistics().getEnqueues().getCount());

        // ensure no dangling messages with fresh broker etc
        broker.stop();
        broker.waitUntilStopped();

        LOG.info("Checking for remaining/hung messages with restart..");
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