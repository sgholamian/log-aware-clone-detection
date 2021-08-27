//,temp,AmqpSendReceiveTest.java,85,120,temp,AmqpConnectionsTest.java,299,335
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSimpleSendOneReceive() throws Exception {

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpSender sender = session.createSender("queue://" + getTestName());
        AmqpReceiver receiver = session.createReceiver("queue://" + getTestName());

        AmqpMessage message = new AmqpMessage();

        final int PAYLOAD_SIZE = 1024 * 1024;

        byte[] payload = new byte[PAYLOAD_SIZE];
        for (int i = 0; i < PAYLOAD_SIZE; i++) {
            payload[i] = (byte) (i % PAYLOAD_SIZE);
        }

        message.setMessageId("msg" + 1);
        message.setMessageAnnotation("serialNo", 1);
        message.setBytes(payload);

        sender.send(message);
        sender.close();

        LOG.info("Attempting to read message with receiver");
        receiver.flow(2);
        AmqpMessage received = receiver.receive(10, TimeUnit.SECONDS);
        assertNotNull("Should have read message", received);
        assertEquals("msg1", received.getMessageId());
        received.accept();

        receiver.close();

        connection.close();
    }

};