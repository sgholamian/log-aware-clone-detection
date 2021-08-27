//,temp,SecurityJMXTest.java,151,154,temp,XBeanSecurityWithGuestTest.java,72,75
//,2
public class xxx {
    protected BrokerService createBroker(String uri) throws Exception {
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};