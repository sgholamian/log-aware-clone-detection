//,temp,AmqpSendReceiveTest.java,85,120,temp,AmqpConnectionsTest.java,299,335
//,3
public class xxx {
    public void doTestSimpleSendOneReceiveOne(Class<?> destType) throws Exception {

        final String address;
        if (Queue.class.equals(destType)) {
            address = "queue://" + getTestName();
        } else {
            address = "topic://" + getTestName();
        }

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpSender sender = session.createSender(address);
        AmqpReceiver receiver = session.createReceiver(address);

        AmqpMessage message = new AmqpMessage();

        message.setMessageId("msg" + 1);
        message.setMessageAnnotation("serialNo", 1);
        message.setText("Test-Message");

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