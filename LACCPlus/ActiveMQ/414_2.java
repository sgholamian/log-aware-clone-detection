//,temp,RedeliveryRestartTest.java,139,180,temp,RedeliveryRestartTest.java,88,137
//,3
public class xxx {
    @org.junit.Test
    public void testValidateRedeliveryFlagAfterRestartNoTx() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(" + broker.getTransportConnectors().get(0).getPublishableConnectString()
            + ")?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        populateDestination(10, destination, connection);

        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage msg = null;
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(20000);
            LOG.info("not redelivered? got: " + msg);
            assertNotNull("got the message", msg);
            assertEquals("first delivery", 1, msg.getLongProperty("JMSXDeliveryCount"));
            assertEquals("not a redelivery", false, msg.getJMSRedelivered());
        }
        consumer.close();

        restartBroker();

        // make failover aware of the restarted auto assigned port
        connection.getTransport().narrow(FailoverTransport.class).add(true, broker.getTransportConnectors().get(0)
                .getPublishableConnectString());

        consumer = session.createConsumer(destination);
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(4000);
            LOG.info("redelivered? got: " + msg);
            assertNotNull("got the message again", msg);
            assertEquals("re delivery flag", true, msg.getJMSRedelivered());
            assertEquals("redelivery count survives restart", 2, msg.getLongProperty("JMSXDeliveryCount"));
            msg.acknowledge();
        }

        // consume the rest that were not redeliveries
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(20000);
            LOG.info("not redelivered? got: " + msg);
            assertNotNull("got the message", msg);
            assertEquals("not a redelivery", false, msg.getJMSRedelivered());
            assertEquals("first delivery", 1, msg.getLongProperty("JMSXDeliveryCount"));
            msg.acknowledge();
        }
        connection.close();
    }

};