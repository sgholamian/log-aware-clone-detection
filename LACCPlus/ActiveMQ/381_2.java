//,temp,RedeliveryRecoveryTest.java,76,112,temp,RedeliveryRestartTest.java,235,273
//,3
public class xxx {
    @org.junit.Test
    public void testValidateRedeliveryFlagAfterRecovery() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString()
            + "?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Destination destination = session.createQueue(queueName);
        populateDestination(1, destination, connection);

        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage msg = (TextMessage) consumer.receive(5000);
        LOG.info("got: " + msg);
        assertNotNull("got the message", msg);
        assertEquals("first delivery", 1, msg.getLongProperty("JMSXDeliveryCount"));
        assertEquals("not a redelivery", false, msg.getJMSRedelivered());

        stopBrokerWithStoreFailure(broker, persistenceAdapterChoice);

        broker = createRestartedBroker();
        broker.start();

        connection.close();

        connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString());
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(true, Session.SESSION_TRANSACTED);
        consumer = session.createConsumer(destination);
        msg = (TextMessage) consumer.receive(10000);
        assertNotNull("got the message again", msg);
        assertEquals("redelivery count survives restart", 2, msg.getLongProperty("JMSXDeliveryCount"));
        assertEquals("re delivery flag", true, msg.getJMSRedelivered());

        session.commit();
        connection.close();
    }

};