//,temp,BrokerPropertiesTest.java,47,59,temp,BrokerPropertiesTest.java,34,44
//,3
public class xxx {
    public void testPropertiesFile() throws Exception {
        BrokerService broker = BrokerFactory.createBroker("properties:org/apache/activemq/config/broker.properties");

        LOG.info("Created broker: " + broker);
        assertNotNull(broker);

        assertEquals("isUseJmx()", false, broker.isUseJmx());
        assertEquals("isPersistent()", false, broker.isPersistent());
        assertEquals("getBrokerName()", "Cheese", broker.getBrokerName());
        broker.stop();
    }

};