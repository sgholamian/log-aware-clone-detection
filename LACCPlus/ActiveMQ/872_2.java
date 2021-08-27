//,temp,ActiveMQSslConnectionFactoryTest.java,124,138,temp,ActiveMQSslConnectionFactoryTest.java,105,122
//,3
public class xxx {
    public void testFailoverSslConnection() throws Exception {
        // Create SSL/TLS connection with trusted cert from truststore.
        String sslUri = "ssl://localhost:61611";
        broker = createSslBroker(sslUri);
        assertNotNull(broker);

        // This should create the connection.
        ActiveMQSslConnectionFactory cf = getFactory("failover:(" + sslUri + ")?maxReconnectAttempts=4");
        cf.setTrustStore("server.keystore");
        cf.setTrustStorePassword("password");
        connection = (ActiveMQConnection)cf.createConnection();
        LOG.info("Created client connection");
        assertNotNull(connection);
        connection.start();
        connection.stop();

        brokerStop();
    }

};