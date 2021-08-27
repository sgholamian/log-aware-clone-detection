//,temp,NetworkRestartTest.java,78,110,temp,NetworkRestartTest.java,45,76
//,3
public class xxx {
    public void testConnectorReAdd() throws Exception {
        MessageConsumer remoteConsumer = remoteSession.createConsumer(included);
        MessageProducer localProducer = localSession.createProducer(included);

        localProducer.send(localSession.createTextMessage("before"));
        Message before = remoteConsumer.receive(1000);
        assertNotNull(before);
        assertEquals("before", ((TextMessage)before).getText());

        // restart connector

        // wait for ack back to localbroker with concurrent store and dispatch, dispatch occurs first
        Thread.sleep(1000);

        NetworkConnector connector = localBroker.getNetworkConnectorByName("networkConnector");

        LOG.info("Removing connector");
        connector.stop();
        localBroker.removeNetworkConnector(connector);

        Thread.sleep(5000);
        LOG.info("Re-adding connector");
        localBroker.addNetworkConnector(connector);
        connector.start();

        Thread.sleep(5000);


        localProducer.send(localSession.createTextMessage("after"));
        Message after = remoteConsumer.receive(3000);
        assertNotNull(after);
        assertEquals("after", ((TextMessage)after).getText());
    }

};