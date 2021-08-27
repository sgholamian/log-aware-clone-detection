//,temp,AbstractVirtualDestTest.java,49,62,temp,KahaDBStoreOpenWireVersionTest.java,226,250
//,3
public class xxx {
    protected void exerciseVirtualTopic(String prefix, String topic) throws Exception {
        ActiveMQConnection connection = new ActiveMQConnectionFactory("vm://localhost").createActiveMQConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(session.createQueue(prefix + topic));
        LOG.info("new consumer for: " + consumer.getDestination());
        MessageProducer producer = session.createProducer(session.createTopic(topic));
        final String body = "To vt:" + topic;
        Message message = sendAndReceiveMessage(session, consumer, producer, body);
        assertNotNull("got message", message);
        assertEquals("got expected message", body, ((TextMessage) message).getText());
        connection.close();
    }

};