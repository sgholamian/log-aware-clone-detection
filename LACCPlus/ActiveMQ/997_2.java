//,temp,KahaDBFastEnqueueTest.java,184,210,temp,JmsSendReceiveStressTest.java,142,161
//,3
public class xxx {
    private void publishMessages(AtomicLong count) throws Exception {
        JmsConnection connection = (JmsConnection) factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        while (count.getAndDecrement() > 0) {
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(payload);
            producer.send(message);
            if ((count.get() % 10000) == 0) {
                LOG.info("Sent message: {}", NUM_SENDS - count.get());
            }
        }
        producer.close();
        connection.close();
    }

};