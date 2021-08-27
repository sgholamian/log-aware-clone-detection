//,temp,JmsSendReceiveWithMessageExpirationTest.java,188,214,temp,JmsSendReceiveWithMessageExpirationTest.java,84,114
//,3
public class xxx {
    public void testConsumeExpiredQueue() throws Exception {

        MessageProducer producer = createProducer(timeToLive);

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

        // sleeps a second longer than the expiration time.
        // Basically waits till queue expires.
        Thread.sleep(timeToLive + 1000);

        // message should have expired.
        assertNull(consumer.receive(1000));
    }

};