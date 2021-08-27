//,temp,ConsumerListenerTest.java,132,144,temp,TempDestDeleteTest.java,130,141
//,3
public class xxx {
    protected MessageConsumer createConsumer(Destination dest) throws JMSException {
        final String consumerText = "Consumer: " + (++consumerCounter);
        LOG.info("Creating consumer: " + consumerText + " on destination: " + dest);

        MessageConsumer consumer = session.createConsumer(dest);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                LOG.info("Received message by: " + consumerText + " message: " + message);
            }
        });
        return consumer;
    }

};