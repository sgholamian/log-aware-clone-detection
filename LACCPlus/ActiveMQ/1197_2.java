//,temp,ActiveMQSslConnectionFactoryTest.java,162,182,temp,ActiveMQSslConnectionFactoryTest.java,140,160
//,2
public class xxx {
    public void testNegativeCreateSslConnectionWithWrongPassword() throws Exception {
        // Create SSL/TLS connection with trusted cert from truststore.
        String sslUri = "ssl://localhost:61611";
        broker = createSslBroker(sslUri);
        assertNotNull(broker);

        // This should FAIL to connect, due to wrong password.
        ActiveMQSslConnectionFactory cf = getFactory(sslUri);
        cf.setTrustStore("server.keystore");
        cf.setTrustStorePassword("wrongPassword");
        try {
            connection = (ActiveMQConnection)cf.createConnection();
        }
        catch (javax.jms.JMSException ignore) {
            // Expected exception
            LOG.info("Expected java.io.Exception [" + ignore + "]");
        }
        assertNull(connection);

        brokerStop();
    }

};