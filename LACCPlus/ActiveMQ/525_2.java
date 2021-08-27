//,temp,BrokerStatisticsPluginTest.java,212,215,temp,SimpleAuthenticationPluginNoUsersTest.java,44,47
//,2
public class xxx {
    protected BrokerService createBroker(String uri) throws Exception {
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};