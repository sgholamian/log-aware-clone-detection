//,temp,JMSLargeMessageSendRecvTest.java,150,183,temp,JMSLargeMessageSendRecvTest.java,93,124
//,3
public class xxx {
    public void doTestSendBytesMessageOfGivenSize(int expectedSize) throws JMSException{
        LOG.info("doTestSendLargeMessage called with expectedSize " + expectedSize);
        byte[] payload = createLargeByteArray(expectedSize);
        assertEquals(expectedSize, payload.length);

        Connection connection = JMSClientContext.INSTANCE.createConnection(getBrokerAmqpConnectionURI());
        long startTime = System.currentTimeMillis();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(testName.getMethodName());
        MessageProducer producer = session.createProducer(queue);
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(payload);
        producer.send(message);
        long endTime = System.currentTimeMillis();
        LOG.info("Returned from send after {} ms", endTime - startTime);

        startTime = System.currentTimeMillis();

        MessageConsumer consumer = session.createConsumer(queue);
        connection.start();
        LOG.info("Calling receive");
        Message receivedMessage = consumer.receive();
        assertNotNull(receivedMessage);
        assertTrue(receivedMessage instanceof BytesMessage);
        BytesMessage receivedBytesMessage = (BytesMessage) receivedMessage;
        assertNotNull(receivedMessage);
        endTime = System.currentTimeMillis();
        LOG.info("Returned from receive after {} ms", endTime - startTime);
        byte[] receivedBytes = new byte[(int) receivedBytesMessage.getBodyLength()];
        receivedBytesMessage.readBytes(receivedBytes);
        assertEquals(expectedSize, receivedBytes.length);
        assertArrayEquals(payload, receivedBytes);
        connection.close();
    }

};