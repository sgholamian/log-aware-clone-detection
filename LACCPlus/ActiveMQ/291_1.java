//,temp,KahaDBStoreOpenWireVersionTest.java,196,224,temp,KahaDBVersionTest.java,96,115
//,3
public class xxx {
    private void populateStore() throws Exception {

        ConnectionFactory factory = new ActiveMQConnectionFactory(broker.getVmConnectorURI());
        Connection connection = factory.createConnection();
        connection.setClientID("test");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test.topic");
        Queue queue = session.createQueue("test.queue");
        MessageConsumer consumer = session.createDurableSubscriber(topic, "test");
        consumer.close();

        MessageProducer producer = session.createProducer(topic);
        producer.setPriority(9);
        for (int i = 0; i < NUM_MESSAGES; i++) {
            Message msg = session.createTextMessage("test message:" + i);
            producer.send(msg);
        }
        LOG.info("sent {} to topic", NUM_MESSAGES);

        producer = session.createProducer(queue);
        for (int i = 0; i < NUM_MESSAGES; i++) {
            Message msg = session.createTextMessage("test message:" + i);
            producer.send(msg);
        }
        LOG.info("sent {} to topic", NUM_MESSAGES);

        connection.close();
    }

};