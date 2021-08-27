//,temp,ActiveMQConnectionFactoryTest.java,157,167,temp,ActiveMQXAConnectionFactoryTest.java,140,150
//,3
public class xxx {
    public void testGetBrokerName() throws URISyntaxException, JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
            "vm://localhost?broker.persistent=false&broker.useJmx=false");
        connection = (ActiveMQConnection)cf.createConnection();
        connection.start();

        String brokerName = connection.getBrokerName();
        LOG.info("Got broker name: " + brokerName);

        assertNotNull("No broker name available!", brokerName);
    }

};