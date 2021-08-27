//,temp,FailoverComplexClusterTest.java,201,238,temp,FailoverPriorityTest.java,128,155
//,3
public class xxx {
    public void testPriorityBackupAndUpdateClients() throws Exception {
        // Broker A
        addBroker(BROKER_A_NAME, createBroker(BROKER_A_NAME));
        addTransportConnector(getBroker(BROKER_A_NAME), "openwire", BROKER_A_CLIENT_TC_ADDRESS, true);
        addNetworkBridge(getBroker(BROKER_A_NAME), "A_2_B_Bridge", "static://(" + BROKER_B_CLIENT_TC_ADDRESS + ")?useExponentialBackOff=false", false, null);
        getBroker(BROKER_A_NAME).start();

        // Broker B
        addBroker(BROKER_B_NAME, createBroker(BROKER_B_NAME));
        addTransportConnector(getBroker(BROKER_B_NAME), "openwire", BROKER_B_CLIENT_TC_ADDRESS, true);
        addNetworkBridge(getBroker(BROKER_B_NAME), "B_2_A_Bridge", "static://(" + BROKER_A_CLIENT_TC_ADDRESS + ")?useExponentialBackOff=false", false, null);
        getBroker(BROKER_B_NAME).start();

        getBroker(BROKER_B_NAME).waitUntilStarted();
        Thread.sleep(1000);

        setClientUrl("failover:(" + BROKER_A_CLIENT_TC_ADDRESS + "," + BROKER_B_CLIENT_TC_ADDRESS + ")?randomize=false&priorityBackup=true&initialReconnectDelay=1000&useExponentialBackOff=false");

        LOG.info("Client URI will be: " + getClientUrl());

        createClients(5);

        // Let's wait a little bit longer just in case it takes a while to realize that the
        // Broker A is the one with higher priority.
        Thread.sleep(5000);

        assertAllConnectedTo(urls.get(BROKER_A_NAME));
    }

};