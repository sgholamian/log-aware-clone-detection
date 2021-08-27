//,temp,RedeliveryRecoveryTest.java,76,112,temp,RedeliveryRestartTest.java,235,273
//,3
public class xxx {
    @org.junit.Test
    public void testValidateRedeliveryFlagAfterRestart() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString()
                + "?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        populateDestination(1, destination, connection);
        MessageConsumer consumer = session.createConsumer(destination);
        Message msg = consumer.receive(5000);
        LOG.info("got: " + msg);
        assertNotNull("got the message", msg);
        assertFalse("got the message", msg.getJMSRedelivered());
        consumer.close();
        connection.close();

        restartBroker();

        connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString()
                + "?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        destination = session.createQueue(queueName);
        consumer = session.createConsumer(destination);

        msg = consumer.receive(5000);
        LOG.info("got: " + msg);
        assertNotNull("got the message", msg);
        assertTrue("got the message has redelivered flag", msg.getJMSRedelivered());

        connection.close();
    }

};