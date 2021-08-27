//,temp,NetworkConnectionsTest.java,130,206,temp,NetworkConnectionsTest.java,78,128
//,3
public class xxx {
    @Test
    public void testNetworkConnectionReAddURI() throws Exception {
        LOG.info("testNetworkConnectionReAddURI is starting...");

        LOG.info("Adding network connector 'NC1'...");
        NetworkConnector nc = localBroker.addNetworkConnector("static:(" + REMOTE_BROKER_TRANSPORT_URI + ")");
        nc.setName("NC1");
        nc.start();
        assertTrue(nc.isStarted());

        LOG.info("Looking up network connector by name...");
        NetworkConnector nc1 = localBroker.getNetworkConnectorByName("NC1");
        assertNotNull("Should find network connector 'NC1'", nc1);
        assertTrue(nc1.isStarted());
        assertEquals(nc, nc1);

        LOG.info("Setting up producer and consumer...");
        ActiveMQQueue destination = new ActiveMQQueue(DESTINATION_NAME);

        ActiveMQConnectionFactory localFactory = new ActiveMQConnectionFactory(LOCAL_BROKER_TRANSPORT_URI);
        Connection localConnection = localFactory.createConnection();
        localConnection.start();
        Session localSession = localConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer localProducer = localSession.createProducer(destination);

        ActiveMQConnectionFactory remoteFactory = new ActiveMQConnectionFactory(REMOTE_BROKER_TRANSPORT_URI);
        Connection remoteConnection = remoteFactory.createConnection();
        remoteConnection.start();
        Session remoteSession = remoteConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer remoteConsumer = remoteSession.createConsumer(destination);

        Message message = localSession.createTextMessage("test");
        localProducer.send(message);

        LOG.info("Testing initial network connection...");
        message = remoteConsumer.receive(10000);
        assertNotNull(message);

        LOG.info("Stopping network connector 'NC1'...");
        nc.stop();
        assertFalse(nc.isStarted());

        LOG.info("Removing network connector...");
        assertTrue(localBroker.removeNetworkConnector(nc));

        nc1 = localBroker.getNetworkConnectorByName("NC1");
        assertNull("Should not find network connector 'NC1'", nc1);

        LOG.info("Re-adding network connector 'NC2'...");
        nc = localBroker.addNetworkConnector("static:(" + REMOTE_BROKER_TRANSPORT_URI + ")");
        nc.setName("NC2");
        nc.start();
        assertTrue(nc.isStarted());

        LOG.info("Looking up network connector by name...");
        NetworkConnector nc2 = localBroker.getNetworkConnectorByName("NC2");
        assertNotNull(nc2);
        assertTrue(nc2.isStarted());
        assertEquals(nc, nc2);

        LOG.info("Testing re-added network connection...");
        message = localSession.createTextMessage("test");
        localProducer.send(message);

        message = remoteConsumer.receive(10000);
        assertNotNull(message);

        LOG.info("Stopping network connector...");
        nc.stop();
        assertFalse(nc.isStarted());

        LOG.info("Removing network connection 'NC2'");
        assertTrue(localBroker.removeNetworkConnector(nc));

        nc2 = localBroker.getNetworkConnectorByName("NC2");
        assertNull("Should not find network connector 'NC2'", nc2);
    }

};