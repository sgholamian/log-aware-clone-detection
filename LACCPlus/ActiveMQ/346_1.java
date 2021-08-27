//,temp,JmsSendReceiveWithMessageExpirationTest.java,188,214,temp,JmsSendReceiveWithMessageExpirationTest.java,84,114
//,3
public class xxx {
    public void testConsumeQueue() throws Exception {

        MessageProducer producer = createProducer(0);

        consumerDestination = session.createQueue(getConsumerSubject());
        producerDestination = session.createQueue(getProducerSubject());

        MessageConsumer consumer = createConsumer();
        connection.start();

        for (int i = 0; i < data.length; i++) {
            Message message = session.createTextMessage(data[i]);
            message.setStringProperty("stringProperty", data[i]);
            message.setIntProperty("intProperty", i);

            if (verbose) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("About to send a queue message: " + message + " with text: " + data[i]);
                }
            }

            producer.send(producerDestination, message);
        }

        // should receive a queue since there is no expiration.
        assertNotNull(consumer.receive(1000));
    }

};