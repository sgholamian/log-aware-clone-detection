//,temp,RetroactiveConsumerTestWithSimpleMessageListTest.java,100,105,temp,XBeanSecurityWithGuestNoCredentialsOnlyTest.java,74,77
//,3
public class xxx {
    @Override
    protected BrokerService createBroker() throws Exception {
        String uri = getBrokerXml();
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};