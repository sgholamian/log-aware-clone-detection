//,temp,ActiveMQSSLAdmin.java,39,71,temp,ActiveMQNIOPlusSSLAdmin.java,39,71
//,2
public class xxx {
    @Override
    public void startServer() throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        // Setup SSL context...
        final File classesDir = new File(ActiveMQNIOPlusSSLAdmin.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        File keystore = new File(classesDir, "../../src/test/resources/keystore");
        final SpringSslContext sslContext = new SpringSslContext();
        sslContext.setKeyStore(keystore.getCanonicalPath());
        sslContext.setKeyStorePassword("password");
        sslContext.setTrustStore(keystore.getCanonicalPath());
        sslContext.setTrustStorePassword("password");
        sslContext.afterPropertiesSet();

        if (broker != null) {
            stopServer();
        }
        if (System.getProperty("basedir") == null) {
            File file = new File(".");
            System.setProperty("basedir", file.getAbsolutePath());
        }
        broker = createBroker();
        broker.setSslContext(sslContext);

        String connectorURI = getConnectorURI();
        TransportConnector connector = broker.addConnector(connectorURI);
        port = connector.getConnectUri().getPort();
        LOG.info("nio+ssl port is {}", port);

        broker.start();
    }

};