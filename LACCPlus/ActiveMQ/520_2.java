//,temp,DoSTest.java,95,98,temp,PluginBrokerTest.java,55,58
//,2
public class xxx {
    protected BrokerService createBroker(String uri) throws Exception {
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};