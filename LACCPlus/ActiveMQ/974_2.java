//,temp,JmsSendReceiveWithMessageExpirationTest.java,258,284,temp,JmsSendReceiveWithMessageExpirationTest.java,221,251
//,3
public class xxx {
    public void testConsumeExpiredTopic() throws Exception {

        MessageProducer producer = createProducer(timeToLive);

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

        // sleeps a second longer than the expiration time.
        // Basically waits till topic expires.
        Thread.sleep(timeToLive + 1000);

        // message should have expired.
        assertNull(consumer.receive(1000));
    }

};