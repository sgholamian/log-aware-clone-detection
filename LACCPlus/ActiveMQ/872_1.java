//,temp,ActiveMQSslConnectionFactoryTest.java,124,138,temp,ActiveMQSslConnectionFactoryTest.java,105,122
//,3
public class xxx {
    public void testFailoverSslConnectionWithKeyAndTrustManagers() throws Exception {
        String sslUri = "ssl://localhost:61611";
        broker = createSslBroker(sslUri);
        assertNotNull(broker);

        ActiveMQSslConnectionFactory cf = getFactory("failover:(" + sslUri + ")?maxReconnectAttempts=4");
        cf.setKeyAndTrustManagers(getKeyManager(), getTrustManager(), new SecureRandom());
        connection = (ActiveMQConnection)cf.createConnection();
        LOG.info("Created client connection");
        assertNotNull(connection);
        connection.start();
        connection.stop();

        brokerStop();
    }

};