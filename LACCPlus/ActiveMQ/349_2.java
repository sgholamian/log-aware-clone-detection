//,temp,AbstractVirtualDestTest.java,49,62,temp,KahaDBStoreOpenWireVersionTest.java,226,250
//,3
public class xxx {
    private void assertStoreIsUsable() throws Exception {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = cf.createConnection();
        connection.setClientID("test");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test.topic");
        Queue queue = session.createQueue("test.queue");

        MessageConsumer queueConsumer = session.createConsumer(queue);
        for (int i = 0; i < NUM_MESSAGES; ++i) {
            TextMessage received = (TextMessage) queueConsumer.receive(1000);
            assertNotNull(received);
        }
        LOG.info("Consumed {} from queue", NUM_MESSAGES);

        MessageConsumer topicConsumer = session.createDurableSubscriber(topic, "test");
        for (int i = 0; i < NUM_MESSAGES; ++i) {
            TextMessage received = (TextMessage) topicConsumer.receive(1000);
            assertNotNull(received);
        }
        LOG.info("Consumed {} from topic", NUM_MESSAGES);

        connection.close();
    }

};