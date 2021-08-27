//,temp,TwoBrokerQueueClientsReconnectTest.java,524,605,temp,TwoBrokerQueueClientsReconnectTest.java,370,445
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public void testDuplicateSendWithNoAuditEnqueueCountStat() throws Exception {
        broker1 = "BrokerA";
        broker2 = "BrokerB";

        NetworkConnector networkConnector = bridgeBrokers(broker1, broker2);

        final AtomicBoolean first = new AtomicBoolean();
        final CountDownLatch gotMessageLatch = new CountDownLatch(1);

        BrokerService brokerService = brokers.get(broker2).broker;
        brokerService.setPersistent(true);
        brokerService.setDeleteAllMessagesOnStartup(true);
        // disable concurrent dispatch otherwise store duplicate suppression will be skipped b/c cursor audit is already
        // disabled so verification of stats will fail - ie: duplicate will be dispatched
        ((KahaDBPersistenceAdapter)brokerService.getPersistenceAdapter()).setConcurrentStoreAndDispatchQueues(false);
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

        // Create queue
        ActiveMQDestination dest = createDestination("TEST.FOO", false);

        // statically include our destination
        networkConnector.addStaticallyIncludedDestination(dest);

        // Run brokers
        startAllBrokers();

        waitForBridgeFormation();

        sendMessages("BrokerA", dest, 1);

        // wait for broker2 to get the initial forward
        Wait.waitFor(new Wait.Condition(){
            @Override
            public boolean isSatisified() throws Exception {
                return brokers.get(broker2).broker.getAdminView().getTotalMessageCount() == 1;
            }
        });

        // message still pending on broker1
        assertEquals("messages message still there", 1, brokers.get(broker1).broker.getAdminView().getTotalMessageCount());

        // allow the bridge to be shutdown and restarted
        gotMessageLatch.countDown();


        // verify message is forwarded after restart
        assertTrue("no messages enqueued on origin", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == brokers.get(broker1).broker.getAdminView().getTotalMessageCount();
            }
        }));

        assertEquals("one messages pending", 1, brokers.get(broker2).broker.getAdminView().getTotalMessageCount());
        assertEquals("one messages enqueued", 1, brokers.get(broker2).broker.getDestination(dest).getDestinationStatistics().getEnqueues().getCount());
    }

};