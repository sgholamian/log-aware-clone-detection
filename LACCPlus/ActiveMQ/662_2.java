//,temp,RetroactiveConsumerTestWithSimpleMessageListTest.java,100,105,temp,XBeanSecurityWithGuestNoCredentialsOnlyTest.java,74,77
//,3
public class xxx {
    protected BrokerService createBroker(String uri) throws Exception {
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};