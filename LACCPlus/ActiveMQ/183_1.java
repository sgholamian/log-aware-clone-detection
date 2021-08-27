//,temp,DestinationListenerTest.java,65,76,temp,ZeroPrefetchConsumerTest.java,52,63
//,3
public class xxx {
    public void testConsumerForcesNotificationOfNewDestination() throws Exception {
        // now lets cause a destination to be created
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQQueue newQueue = new ActiveMQQueue("Test.Cheese");
        session.createConsumer(newQueue);

        Thread.sleep(3000);

        assertThat(newQueue, isIn(newDestinations));

        LOG.info("New destinations are: " + newDestinations);
    }

};