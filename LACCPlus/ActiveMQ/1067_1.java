//,temp,RequestReplyTempDestRemovalAdvisoryRaceTest.java,203,262,temp,RequestReplyTempDestRemovalAdvisoryRaceTest.java,108,168
//,3
public class xxx {
    public void testTempDestRace() throws Exception {
        // non duplex
        bridgeBrokers(BROKER_A, BROKER_B, false, 3);
        bridgeBrokers(BROKER_B, BROKER_A, false, 3);
        bridgeBrokers(BROKER_B, BROKER_C, false, 3);
        bridgeBrokers(BROKER_C, BROKER_B, false, 3);

        startAllBrokers();

        waitForBridgeFormation(1);

        HashSet<NetworkBridge> bridgesStart = new HashSet<NetworkBridge>();
        for (NetworkConnector networkConnector : networkConnectors) {
            bridgesStart.addAll(networkConnector.activeBridges());
        }

        slowDownAdvisoryDispatch();
        noConsumerAdvisory();
        forwardFailureAdvisory();


        // set up respondents
        ExecutorService respondentThreadPool = Executors.newFixedThreadPool(50);
        BrokerItem brokerA = brokers.get(BROKER_A);
        ActiveMQConnectionFactory brokerAFactory = new ActiveMQConnectionFactory(brokerA.broker.getTransportConnectorByScheme("tcp").getName()
                + "?jms.watchTopicAdvisories=false");
        brokerAFactory.setAlwaysSyncSend(true);
        for (int i = 0; i < NUM_RESPONDENTS; i++) {
            respondentThreadPool.execute(new EchoRespondent(brokerAFactory));
        }

        // fire off sends
        ExecutorService senderThreadPool = Executors.newCachedThreadPool();
        BrokerItem brokerC = brokers.get(BROKER_C);
        ActiveMQConnectionFactory brokerCFactory = new ActiveMQConnectionFactory(brokerC.broker.getTransportConnectorByScheme("tcp").getName()
                + "?jms.watchTopicAdvisories=false");
        for (int i = 0; i < NUM_SENDS; i++) {
            senderThreadPool.execute(new MessageSender(brokerCFactory));
        }

        senderThreadPool.shutdown();
        senderThreadPool.awaitTermination(30, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
        LOG.info("shutting down");
        shutdown.compareAndSet(false, true);

        HashSet<NetworkBridge> bridgesEnd = new HashSet<NetworkBridge>();
        for (NetworkConnector networkConnector : networkConnectors) {
            bridgesEnd.addAll(networkConnector.activeBridges());
        }
        assertEquals("no new bridges created", bridgesStart, bridgesEnd);

        // validate success or error or dlq
        LOG.info("received: " + responseReceived.get() + ", respondent error: " + respondentSendError.get()
                + ", noConsumerCount: " + sendsWithNoConsumers.get()
                + ", forwardFailures: " + forwardFailures.get());
        assertEquals("success or error", NUM_SENDS, respondentSendError.get() + forwardFailures.get()
                + responseReceived.get() + sendsWithNoConsumers.get());

    }

};