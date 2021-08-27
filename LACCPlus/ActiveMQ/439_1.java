//,temp,RedeliveryRestartWithExceptionTest.java,223,271,temp,RedeliveryRestartWithExceptionTest.java,92,157
//,3
public class xxx {
    @org.junit.Test
    public void testValidateRedeliveryFlagOnNonPersistentAfterTransientFailureConnectionDrop() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString()
            + "?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        populateDestination(10, destination, connection, false);
        TextMessage msg = null;
        MessageConsumer consumer = session.createConsumer(destination);
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(5000);
            assertNotNull("got the message", msg);
            assertFalse("not redelivered", msg.getJMSRedelivered());
        }

        connection.getTransport().narrow(TcpTransport.class).getTransportListener().onException(new IOException("Die"));

        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        destination = session.createQueue(queueName);
        consumer = session.createConsumer(destination);

        // consume the messages that were previously delivered
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(4000);
            LOG.info("redelivered? got: " + msg);
            assertNotNull("got the message again", msg);
            assertEquals("redelivery flag set on:" + i, true, msg.getJMSRedelivered());
            assertTrue("redelivery count survives reconnect for:" + i, msg.getLongProperty("JMSXDeliveryCount") > 1);
            msg.acknowledge();
        }

        // consume the rest that were not redeliveries
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) consumer.receive(4000);
            LOG.info("not redelivered? got: " + msg);
            assertNotNull("got the message", msg);
            assertEquals("not a redelivery", false, msg.getJMSRedelivered());
            assertEquals("first delivery", 1, msg.getLongProperty("JMSXDeliveryCount"));
            msg.acknowledge();
        }
        connection.close();
    }

};