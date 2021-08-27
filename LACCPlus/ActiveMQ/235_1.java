//,temp,ActiveMQSSLAdmin.java,78,98,temp,ActiveMQNIOPlusSSLAdmin.java,78,97
//,3
public class xxx {
    @Override
    public void createConnectionFactory(String name) {
        try {
            LOG.debug("Creating a connection factory using port {}", port);
            final JmsConnectionFactory factory =
                new JmsConnectionFactory("amqps://localhost:" + port + "?transport.verifyHost=false");

            SpringSslContext sslContext = (SpringSslContext) broker.getSslContext();

            System.setProperty("javax.net.ssl.trustStore", sslContext.getTrustStore());
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            System.setProperty("javax.net.ssl.trustStoreType", "jks");
            System.setProperty("javax.net.ssl.keyStore", sslContext.getKeyStore());
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            System.setProperty("javax.net.ssl.keyStoreType", "jks");

            context.bind(name, factory);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

};