//,temp,XBeanConfigTest.java,115,119,temp,RetroactiveConsumerWithMessageQueryTest.java,98,103
//,3
public class xxx {
    protected BrokerService createBroker() throws Exception {
        String uri = "org/apache/activemq/xbean/activemq-policy.xml";
        LOG.info("Loading broker configuration from the classpath with URI: " + uri);
        return BrokerFactory.createBroker(new URI("xbean:" + uri));
    }

};