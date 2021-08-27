//,temp,JmsSendReceiveWithMessageExpirationTest.java,258,284,temp,JmsSendReceiveWithMessageExpirationTest.java,221,251
//,3
public class xxx {
    public void testConsumeTopic() throws Exception {

        MessageProducer producer = createProducer(0);

        consumerDestination = session.createTopic(getConsumerSubject());
        producerDestination = session.createTopic(getProducerSubject());

        MessageConsumer consumer = createConsumer();
        connection.start();

        for (int i = 0; i < data.length; i++) {
            Message message = session.createTextMessage(data[i]);
            message.setStringProperty("stringProperty", data[i]);
            message.setIntProperty("intProperty", i);

            if (verbose) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("About to send a topic message: " + message + " with text: " + data[i]);
                }
            }

            producer.send(producerDestination, message);
        }

        // should receive a topic since there is no expiration.
        assertNotNull(consumer.receive(1000));
    }

};