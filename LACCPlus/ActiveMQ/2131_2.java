//,temp,JmsConsumerClient.java,245,259,temp,JmsConsumerClient.java,229,243
//,3
public class xxx {
    public MessageConsumer createJmsConsumer(Destination dest) throws JMSException {
        if (client.isDurable()) {
            String clientName = getClientName();
            if (clientName == null) {
                clientName = "JmsConsumer";
                setClientName(clientName);
            }
            LOG.info("Creating durable subscriber (" + clientName + ") to: " + dest.toString());
            jmsConsumer = getSession().createDurableSubscriber((Topic) dest, clientName);
        } else {
            LOG.info("Creating non-durable consumer to: " + dest.toString());
            jmsConsumer = getSession().createConsumer(dest);
        }
        return jmsConsumer;
    }

};