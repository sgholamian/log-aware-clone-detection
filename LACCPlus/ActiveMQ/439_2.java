//,temp,RedeliveryRestartWithExceptionTest.java,223,271,temp,RedeliveryRestartWithExceptionTest.java,92,157
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
        populateDestination(10, destination, connection, true);
        TextMessage msg = null;
        MessageConsumer consumer = session.createConsumer(destination);
        Exception expectedException = null;
        try {
            for (int i = 0; i < 5; i++) {
                msg = (TextMessage) consumer.receive(5000);
                LOG.info("not redelivered? got: " + msg);
                assertNotNull("got the message", msg);
                assertTrue("Should not receive the 5th message", i < 4);
                //The first 4 messages will be ok but the 5th one should hit an exception in updateMessage and should not be delivered
            }
        } catch (Exception e) {
            //Expecting an exception and disconnect on the 5th message
            LOG.info("Got expected:", e);
            expectedException = e;
        }
        assertNotNull("Expecting an exception when updateMessage fails", expectedException);                
        
        consumer.close();
        safeCloseConnection(connection);
        
        restartBroker();
        
        connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getPublishableConnectString()
            + "?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        destination = session.createQueue(queueName);
        consumer = session.createConsumer(destination);
        
        
        // consume the messages that were previously delivered
        for (int i = 0; i < 4; i++) {
            msg = (TextMessage) consumer.receive(4000);
            LOG.info("redelivered? got: " + msg);
            assertNotNull("got the message again", msg);
            assertEquals("re delivery flag", true, msg.getJMSRedelivered());
            assertTrue("redelivery count survives restart", msg.getLongProperty("JMSXDeliveryCount") > 1);
            msg.acknowledge();
        }
        

        // consume the rest that were not redeliveries
        for (int i = 0; i < 6; i++) {
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