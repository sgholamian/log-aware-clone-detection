//,temp,SimpleAuthenticationPluginTest.java,70,73,temp,XBeanSecurityTest.java,48,51
//,2
public class xxx {
    protected BrokerService createBroker(String uri) throws Exception {
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};