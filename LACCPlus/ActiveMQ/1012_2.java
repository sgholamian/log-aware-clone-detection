//,temp,JMSLargeMessageSendRecvTest.java,150,183,temp,JMSLargeMessageSendRecvTest.java,93,124
//,3
public class xxx {
    public void doTestSendTextMessageOfGivenSize(int expectedSize) throws JMSException{
        LOG.info("doTestSendLargeMessage called with expectedSize " + expectedSize);
        String payload = createLargeString(expectedSize);
        assertEquals(expectedSize, payload.getBytes().length);

        Connection connection = JMSClientContext.INSTANCE.createConnection(getBrokerAmqpConnectionURI());
        long startTime = System.currentTimeMillis();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(testName.getMethodName());
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        message.setText(payload);
        producer.send(message);
        long endTime = System.currentTimeMillis();
        LOG.info("Returned from send after {} ms", endTime - startTime);

        startTime = System.currentTimeMillis();
        MessageConsumer consumer = session.createConsumer(queue);
        connection.start();
        LOG.info("Calling receive");
        Message receivedMessage = consumer.receive();
        assertNotNull(receivedMessage);
        assertTrue(receivedMessage instanceof TextMessage);
        TextMessage receivedTextMessage = (TextMessage) receivedMessage;
        assertNotNull(receivedMessage);
        endTime = System.currentTimeMillis();
        LOG.info("Returned from receive after {} ms", endTime - startTime);
        String receivedText = receivedTextMessage.getText();
        assertEquals(expectedSize, receivedText.getBytes().length);
        assertEquals(payload, receivedText);
        connection.close();
    }

};