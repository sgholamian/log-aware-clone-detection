//,temp,RedeliveryRestartTest.java,139,180,temp,RedeliveryRestartTest.java,88,137
//,3
public class xxx {
    @org.junit.Test
    public void testDurableSubRedeliveryFlagAfterRestartNotSupported() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(" + broker.getTransportConnectors().get(0).getPublishableConnectString()
            + ")?jms.prefetchPolicy.all=0");
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.setClientID("id");
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        ActiveMQTopic destination = new ActiveMQTopic(queueName);

        TopicSubscriber durableSub = session.createDurableSubscriber(destination, "id");

        populateDestination(10, destination, connection);

        TextMessage msg = null;
        for (int i = 0; i < 5; i++) {
            msg = (TextMessage) durableSub.receive(20000);
            LOG.info("not redelivered? got: " + msg);
            assertNotNull("got the message", msg);
            assertEquals("first delivery", 1, msg.getLongProperty("JMSXDeliveryCount"));
            assertEquals("not a redelivery", false, msg.getJMSRedelivered());
        }
        durableSub.close();

        restartBroker();

        // make failover aware of the restarted auto assigned port
        connection.getTransport().narrow(FailoverTransport.class).add(true, broker.getTransportConnectors().get(0)
                .getPublishableConnectString());

        durableSub = session.createDurableSubscriber(destination, "id");
        for (int i = 0; i < 10; i++) {
            msg = (TextMessage) durableSub.receive(4000);
            LOG.info("redelivered? got: " + msg);
            assertNotNull("got the message again", msg);
            assertEquals("no reDelivery flag", false, msg.getJMSRedelivered());
            msg.acknowledge();
        }
        connection.close();
    }

};