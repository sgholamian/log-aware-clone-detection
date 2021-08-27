//,temp,ConsumerListenerTest.java,132,144,temp,TempDestDeleteTest.java,130,141
//,3
public class xxx {
    protected Session createConsumer() throws JMSException {
        final String consumerText = "Consumer: " + (++consumerCounter);
        LOG.info("Creating consumer: " + consumerText + " on destination: " + destination);

        Session answer = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = answer.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                LOG.info("Received message by: " + consumerText + " message: " + message);
            }
        });
        return answer;
    }

};