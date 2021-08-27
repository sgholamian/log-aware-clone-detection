//,temp,TwoBrokerQueueClientsReconnectTest.java,524,605,temp,TwoBrokerQueueClientsReconnectTest.java,370,445
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void testDuplicateSend() throws Exception {
        broker1 = "BrokerA";
        broker2 = "BrokerB";

        // enable producer audit for the network connector, off by default b/c of interference with composite
        // dests and virtual topics
        brokers.get(broker2).broker.getTransportConnectors().get(0).setAuditNetworkProducers(true);
        bridgeBrokers(broker1, broker2);

        final AtomicBoolean first = new AtomicBoolean();
        final CountDownLatch gotMessageLatch = new CountDownLatch(1);

        BrokerService brokerService = brokers.get(broker2).broker;
        brokerService.setPersistent(true);
        brokerService.setDeleteAllMessagesOnStartup(true);
        brokerService.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void send(final ProducerBrokerExchange producerExchange,
                                     org.apache.activemq.command.Message messageSend)
                            throws Exception {
                        super.send(producerExchange, messageSend);
                        if (first.compareAndSet(false, true)) {
                            producerExchange.getConnectionContext().setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        LOG.info("Waiting for recepit");
                                        assertTrue("message received on time", gotMessageLatch.await(60, TimeUnit.SECONDS));
                                        LOG.info("Stopping connection post send and receive and multiple producers");
                                        producerExchange.getConnectionContext().getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
        });

        // Run brokers
        startAllBrokers();

        waitForBridgeFormation();

        // Create queue
        Destination dest = createDestination("TEST.FOO", false);

        MessageConsumer client2 = createConsumer(broker2, dest);

        sendMessages("BrokerA", dest, 1);

        assertEquals("Client got message", 1, receiveExactMessages(client2, 1));
        client2.close();
        gotMessageLatch.countDown();

        // message still pending on broker1
        assertEquals("messages message still there", 1, brokers.get(broker1).broker.getAdminView().getTotalMessageCount());

        client2 = createConsumer(broker2, dest);

        LOG.info("Let the second client receive the rest of the messages");
        assertEquals("no duplicate message", 0, receiveAllMessages(client2));
        assertEquals("no duplicate message", 0, receiveAllMessages(client2));

        assertEquals("no messages enqueued", 0, brokers.get(broker2).broker.getAdminView().getTotalMessageCount());
        assertTrue("no messages enqueued on origin", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == brokers.get(broker1).broker.getAdminView().getTotalMessageCount();
            }
        }));
    }

};