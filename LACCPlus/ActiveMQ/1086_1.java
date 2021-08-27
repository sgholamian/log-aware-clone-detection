//,temp,DurableSubscriptionWithNoLocalTest.java,380,442,temp,DurableSubscriptionWithNoLocalTest.java,260,314
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testInvertedDurableSubWithNoLocalChangeAfterRestart() throws Exception {
        TopicConnection connection = factory.createTopicConnection();

        connection.setClientID(getClientId());
        connection.start();

        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(getDestinationName());
        TopicPublisher publisher = session.createPublisher(topic);

        LOG.debug("Create DurableSubscriber with noLocal = true");
        TopicSubscriber subscriber = session.createSubscriber(topic);
        TopicSubscriber durableSub = session.createDurableSubscriber(topic, getSubscriptionName(), null, false);

        LOG.debug("Sending " + MSG_COUNT + " messages to topic");
        for (int i = 0; i < MSG_COUNT; i++) {
            publisher.publish(session.createMessage());
        }

        LOG.info("Attempting to receive messages from non-durable subscriber");
        for (int i = 0; i < MSG_COUNT; i++) {
            assertNotNull(subscriber.receive(500));
        }

        LOG.info("Attempting to receive messages from (noLocal=false) durable subscriber");
        for (int i = 0; i < MSG_COUNT; i++) {
            assertNotNull(durableSub.receive(500));
        }

        LOG.debug("Sending " + MSG_COUNT + " messages to topic");
        for (int i = 0; i < MSG_COUNT; i++) {
            publisher.publish(session.createMessage());
        }

        tearDown();
        createBroker(false);

        connection = factory.createTopicConnection();
        connection.setClientID(getClientId());
        connection.start();

        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(getDestinationName());
        publisher = session.createPublisher(topic);

        LOG.debug("Create DurableSubscriber with noLocal=true");
        durableSub = session.createDurableSubscriber(topic, getSubscriptionName(), null, true);

        LOG.info("Attempting to receive messages from (noLocal=true) subscriber");
        assertNull(durableSub.receive(500));

        LOG.debug("Sending " + MSG_COUNT + " messages to topic");
        for (int i = 0; i < MSG_COUNT; i++) {
            publisher.publish(session.createMessage());
        }

        LOG.info("Attempting to receive messages from (noLocal=true) subscriber");
        assertNull(durableSub.receive(500));

        // Should be empty now
        assertNull(durableSub.receive(100));
    }

};