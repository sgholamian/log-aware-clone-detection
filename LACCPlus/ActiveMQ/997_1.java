//,temp,KahaDBFastEnqueueTest.java,184,210,temp,JmsSendReceiveStressTest.java,142,161
//,3
public class xxx {
    private void publishMessages(AtomicLong count, int expiry) throws Exception {
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.setWatchTopicAdvisories(false);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(destination);
        Long start = System.currentTimeMillis();
        long i = 0l;
        while ( (i=count.getAndDecrement()) > 0) {
            Message message = null;
            if (useBytesMessage) {
                message = session.createBytesMessage();
                ((BytesMessage) message).writeBytes(payloadString.getBytes());
            } else {
                message = session.createTextMessage(payloadString);
            }
            producer.send(message, DeliveryMode.PERSISTENT, 5, expiry);
            if (i != toSend && i%sampleRate == 0) {
                long now = System.currentTimeMillis();
                LOG.info("Remainder: " + i + ", rate: " + sampleRate * 1000 / (now - start) + "m/s" );
                start = now;
            }
        }
        connection.syncSendPacket(new ConnectionControl());
        connection.close();
    }

};