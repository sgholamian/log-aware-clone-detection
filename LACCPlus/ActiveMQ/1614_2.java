//,temp,JmsTopicCompositeSendReceiveTest.java,42,54,temp,JmsQueueTopicCompositeSendReceiveTest.java,41,54
//,3
public class xxx {
    protected void setUp() throws Exception {
        deliveryMode = DeliveryMode.NON_PERSISTENT;
        topic = false;
        super.setUp();
        consumerDestination2 = consumeSession.createTopic("FOO.BAR.HUMBUG2");
        LOG.info("Created  consumer destination: " + consumerDestination2 + " of type: " + consumerDestination2.getClass());
        if (durable) {
            LOG.info("Creating durable consumer");
            consumer2 = consumeSession.createDurableSubscriber((Topic) consumerDestination2, getName());
        } else {
            consumer2 = consumeSession.createConsumer(consumerDestination2);
        }

    }

};