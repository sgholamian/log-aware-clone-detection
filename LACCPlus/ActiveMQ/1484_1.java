//,temp,BrokerPropertiesTest.java,47,59,temp,BrokerPropertiesTest.java,34,44
//,3
public class xxx {
    public void testVmBrokerPropertiesFile() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?brokerConfig=properties:org/apache/activemq/config/broker.properties");
        Connection connection = factory.createConnection();
        BrokerService broker = BrokerRegistry.getInstance().lookup("Cheese");
        LOG.info("Found broker : " + broker);
        assertNotNull(broker);

        assertEquals("isUseJmx()", false, broker.isUseJmx());
        assertEquals("isPersistent()", false, broker.isPersistent());
        assertEquals("getBrokerName()", "Cheese", broker.getBrokerName());
        connection.close();
        broker.stop();
    }

};