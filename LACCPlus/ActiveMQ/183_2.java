//,temp,DestinationListenerTest.java,65,76,temp,ZeroPrefetchConsumerTest.java,52,63
//,3
public class xxx {
    public void testCannotUseMessageListener() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(queue);

        MessageListener listener = new SpringConsumer();
        try {
            consumer.setMessageListener(listener);
            fail("Should have thrown JMSException as we cannot use MessageListener with zero prefetch");
        } catch (JMSException e) {
            LOG.info("Received expected exception : " + e);
        }
    }

};