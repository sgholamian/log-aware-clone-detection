//,temp,JmsProducerClient.java,268,281,temp,JmsProducerClient.java,253,266
//,3
public class xxx {
    public MessageProducer createJmsProducer() throws JMSException {
        jmsProducer = getSession().createProducer(null);
        if (client.getDeliveryMode().equalsIgnoreCase(JmsProducerProperties.DELIVERY_MODE_PERSISTENT)) {
            LOG.info("Creating producer to possible multiple destinations with persistent delivery.");
            jmsProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } else if (client.getDeliveryMode().equalsIgnoreCase(JmsProducerProperties.DELIVERY_MODE_NON_PERSISTENT)) {
            LOG.info("Creating producer to possible multiple destinations with non-persistent delivery.");
            jmsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } else {
            LOG.warn("Unknown deliveryMode value. Defaulting to non-persistent.");
            jmsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        }
        return jmsProducer;
    }

};