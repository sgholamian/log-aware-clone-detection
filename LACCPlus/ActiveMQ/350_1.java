//,temp,AbstractVirtualDestTest.java,64,77,temp,PooledConnectionSecurityExceptionTest.java,256,277
//,3
public class xxx {
    protected void exerciseCompositeQueue(String dest, String consumerQ) throws Exception {
        ActiveMQConnection connection = new ActiveMQConnectionFactory("vm://localhost").createActiveMQConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(session.createQueue(consumerQ));
        LOG.info("new consumer for: " + consumer.getDestination());
        MessageProducer producer = session.createProducer(session.createQueue(dest));
        final String body = "To cq:" + dest;
        Message message = sendAndReceiveMessage(session, consumer, producer, body);
        assertNotNull("got message", message);
        assertEquals("got expected message", body, ((TextMessage) message).getText());
        connection.close();
    }

};