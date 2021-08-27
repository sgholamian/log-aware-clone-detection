//,temp,FailoverComplexClusterTest.java,201,238,temp,FailoverPriorityTest.java,128,155
//,3
public class xxx {
    public void testFailOverWithUpdateClientsOnRemove() throws Exception{
        // Broker A
        addBroker(BROKER_A_NAME, createBroker(BROKER_A_NAME));
        TransportConnector connectorA = getBroker(BROKER_A_NAME).addConnector(BROKER_A_CLIENT_TC_ADDRESS);
        connectorA.setName("openwire");
        connectorA.setRebalanceClusterClients(true);
        connectorA.setUpdateClusterClients(true);
        connectorA.setUpdateClusterClientsOnRemove(true); //If set to false the test succeeds.
        addNetworkBridge(getBroker(BROKER_A_NAME), "A_2_B_Bridge", "static://(" + BROKER_B_CLIENT_TC_ADDRESS + ")?useExponentialBackOff=false", false, null);
        getBroker(BROKER_A_NAME).start();

        // Broker B
        addBroker(BROKER_B_NAME, createBroker(BROKER_B_NAME));
        TransportConnector connectorB = getBroker(BROKER_B_NAME).addConnector(BROKER_B_CLIENT_TC_ADDRESS);
        connectorB.setName("openwire");
        connectorB.setRebalanceClusterClients(true);
        connectorB.setUpdateClusterClients(true);
        connectorB.setUpdateClusterClientsOnRemove(true); //If set to false the test succeeds.
        addNetworkBridge(getBroker(BROKER_B_NAME), "B_2_A_Bridge", "static://(" + BROKER_A_CLIENT_TC_ADDRESS + ")?useExponentialBackOff=false", false, null);
        getBroker(BROKER_B_NAME).start();

        getBroker(BROKER_B_NAME).waitUntilStarted();
        Thread.sleep(1000);

        // create client connecting only to A. It should receive broker B address whet it connects to A.
        setClientUrl("failover:(" + BROKER_A_CLIENT_TC_ADDRESS + ")?useExponentialBackOff=true");
        createClients(1);
        Thread.sleep(5000);

        // We stop broker A.
        logger.info("Stopping broker A whose address is: {}", BROKER_A_CLIENT_TC_ADDRESS);
        getBroker(BROKER_A_NAME).stop();
        getBroker(BROKER_A_NAME).waitUntilStopped();
        Thread.sleep(5000);

        // Client should failover to B.
        assertAllConnectedTo(BROKER_B_CLIENT_TC_ADDRESS);
    }

};