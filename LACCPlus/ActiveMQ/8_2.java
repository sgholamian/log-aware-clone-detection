//,temp,XBeanConfigTest.java,115,119,temp,RetroactiveConsumerWithMessageQueryTest.java,98,103
//,3
public class xxx {
    @Override
    protected BrokerService createBroker() throws Exception {
        String uri = getBrokerXml();
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};