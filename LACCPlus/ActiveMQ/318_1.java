//,temp,ActiveMQConnectionFactoryTest.java,268,295,temp,ActiveMQXAConnectionFactoryTest.java,559,594
//,3
public class xxx {
    protected void assertCreateConnection(String uri) throws Exception {
        // Start up a broker with a tcp connector.
        broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.setAdvisorySupport(false);
        broker.setSchedulerSupport(false);
        TransportConnector connector = broker.addConnector(uri);
        broker.start();

        URI temp = new URI(uri);
        // URI connectURI = connector.getServer().getConnectURI();
        // TODO this sometimes fails when using the actual local host name
        URI currentURI = new URI(connector.getPublishableConnectString());

        // sometimes the actual host name doesn't work in this test case
        // e.g. on OS X so lets use the original details but just use the actual
        // port
        URI connectURI = new URI(temp.getScheme(), temp.getUserInfo(), temp.getHost(), currentURI.getPort(),
                                 temp.getPath(), temp.getQuery(), temp.getFragment());

        LOG.info("connection URI is: " + connectURI);

        // This should create the connection.
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(connectURI);
        connection = (ActiveMQConnection)cf.createConnection();
        assertNotNull(connection);
    }

};