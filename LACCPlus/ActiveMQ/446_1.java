//,temp,FailoverTransactionTest.java,382,478,temp,FailoverTransactionTest.java,271,372
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void testFailoverSendReplyLost() throws Exception {

        broker = createBroker(true);
        setDefaultPersistenceAdapter(broker);

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void send(ProducerBrokerExchange producerExchange,
                                     org.apache.activemq.command.Message messageSend)
                            throws Exception {
                        // so send will hang as if reply is lost
                        super.send(producerExchange, messageSend);
                        producerExchange.getConnectionContext().setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker post send...");
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

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")?jms.watchTopicAdvisories=false");
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(destination);
        final CountDownLatch sendDoneLatch = new CountDownLatch(1);
        // broker will die on send reply so this will hang till restart
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async send...");
                try {
                    produceMessage(session, destination);
                } catch (JMSException e) {
                    //assertTrue(e instanceof TransactionRolledBackException);
                    LOG.error("got send exception: ", e);
                    fail("got unexpected send exception" + e);
                }
                sendDoneLatch.countDown();
                LOG.info("done async send");
            }
        });

        // will be stopped by the plugin
        broker.waitUntilStopped();
        broker = createBroker(false, url);
        setDefaultPersistenceAdapter(broker);
        LOG.info("restarting....");
        broker.start();

        assertTrue("message sent through failover", sendDoneLatch.await(30, TimeUnit.SECONDS));

        // new transaction
        Message msg = consumer.receive(20000);
        LOG.info("Received: " + msg);
        assertNotNull("we got the message", msg);
        assertNull("we got just one message", consumer.receive(2000));
        consumer.close();
        connection.close();

        // verify stats
        assertEquals("no newly queued messages", 0, ((RegionBroker) broker.getRegionBroker()).getDestinationStatistics().getEnqueues().getCount());
        assertEquals("1 dequeue", 1, ((RegionBroker) broker.getRegionBroker()).getDestinationStatistics().getDequeues().getCount());

        // ensure no dangling messages with fresh broker etc
        broker.stop();
        broker.waitUntilStopped();

        LOG.info("Checking for remaining/hung messages with second restart..");
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