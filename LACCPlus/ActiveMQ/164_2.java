//,temp,ConsumeUncompressedCompressedMessageTest.java,155,166,temp,AMQ3145Test.java,111,122
//,3
public class xxx {
    private void createProducerAndSendMessages(int numToSend) throws Exception {
        queue = session.createQueue("test1");
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < numToSend; i++) {
            TextMessage message = session.createTextMessage(MESSAGE_TEXT + i);
            if (i  != 0 && i % 50000 == 0) {
                LOG.info("sent: " + i);
            }
            producer.send(message);
        }
        producer.close();
    }

};