//,temp,VerifyNetworkConsumersDisconnectTest.java,145,200,temp,VerifyNetworkConsumersDisconnectTest.java,84,143
//,3
public class xxx {
    public void testConsumerOnEachBroker() throws Exception {
        bridge("Broker0", "Broker1");
        if (!DUPLEX) bridge("Broker1", "Broker0");

        bridge("Broker1", "Broker2");
        if (!DUPLEX) bridge("Broker2", "Broker1");

        startAllBrokers();
        waitForBridgeFormation(brokers.get("Broker0").broker, 1, 0);
        waitForBridgeFormation(brokers.get("Broker2").broker, 1, 0);
        waitForBridgeFormation(brokers.get("Broker1").broker, 1, 0);
        waitForBridgeFormation(brokers.get("Broker1").broker, 1, 1);

        Destination dest = createDestination("TEST.FOO", false);

        // Setup consumers
        for (int i = 0; i < BROKER_COUNT; i++) {
            consumerMap.put("Consumer:" + i + ":0", createConsumer("Broker" + i, dest));
        }

        assertExactConsumersConnect("Broker0", 3, 1, TIMEOUT);
        assertExactConsumersConnect("Broker2", 3, 1, TIMEOUT);
        // piggy in the middle
        assertExactConsumersConnect("Broker1", 3, 1, TIMEOUT);

        assertNoUnhandledExceptions();

        LOG.info("Complete the mesh - 0->2");

        // shorter route
        NetworkConnector nc = bridge("Broker0", "Broker2");
        nc.setBrokerName("Broker0");
        nc.start();


        if (!DUPLEX) {
            LOG.info("... complete the mesh - 2->0");
            nc = bridge("Broker2", "Broker0");
            nc.setBrokerName("Broker2");
            nc.start();
        }

        // wait for consumers to get propagated
        for (int i = 0; i < BROKER_COUNT; i++) {
        	assertExactConsumersConnect("Broker" + i, 3, 1, TIMEOUT);
        }

        // reverse order close
        consumerMap.get("Consumer:" + 2 + ":0").close();
        TimeUnit.SECONDS.sleep(1);
        consumerMap.get("Consumer:" + 1 + ":0").close();
        TimeUnit.SECONDS.sleep(1);
        consumerMap.get("Consumer:" + 0 + ":0").close();

        LOG.info("Check for no consumers..");
        for (int i = 0; i < BROKER_COUNT; i++) {
        	assertExactConsumersConnect("Broker" + i, 0, 0, TIMEOUT);
        }

    }

};