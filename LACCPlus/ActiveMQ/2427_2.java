//,temp,NetworkConnectionsTest.java,130,206,temp,NetworkConnectionsTest.java,78,128
//,3
public class xxx {
    @Test
    public void testNetworkConnectionRestart() throws Exception {
        LOG.info("testNetworkConnectionRestart is starting...");

        LOG.info("Adding network connector...");
        NetworkConnector nc = localBroker.addNetworkConnector("static:(" + REMOTE_BROKER_TRANSPORT_URI + ")");
        nc.setName("NC1");
        nc.start();
        assertTrue(nc.isStarted());

        LOG.info("Setting up Message Producer and Consumer");
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

        LOG.info("Stopping network connection...");
        nc.stop();
        assertFalse(nc.isStarted());

        LOG.info("Sending 2nd message...");
        message = localSession.createTextMessage("test stop");
        localProducer.send(message);

        message = remoteConsumer.receive(1000);
        assertNull("Message should not have been delivered since NetworkConnector was stopped", message);

        LOG.info("(Re)starting network connection...");
        nc.start();
        assertTrue(nc.isStarted());

        LOG.info("Wait for 2nd message to get forwarded and received...");
        message = remoteConsumer.receive(10000);
        assertNotNull("Should have received 2nd message", message);
    }

};