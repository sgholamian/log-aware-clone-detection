//,temp,DestinationListenerTest.java,78,91,temp,JMSClientSimpleAuthTest.java,172,185
//,3
public class xxx {
    public void testProducerForcesNotificationOfNewDestination() throws Exception {
        // now lets cause a destination to be created
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQQueue newQueue = new ActiveMQQueue("Test.Beer");
        MessageProducer producer = session.createProducer(newQueue);
        TextMessage message = session.createTextMessage("<hello>world</hello>");
        producer.send(message);

        Thread.sleep(3000);

        assertThat(newQueue, isIn(newDestinations));

        LOG.info("New destinations are: " + newDestinations);
    }

};