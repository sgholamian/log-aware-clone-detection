//,temp,AmqpSendReceiveTest.java,710,787,temp,AmqpSendReceiveTest.java,199,259
//,3
public class xxx {
    @Test(timeout = 60000)
    @Repeat(repetitions = 1)
    public void testAdvancedLinkFlowControl() throws Exception {
        final int MSG_COUNT = 20;

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpSender sender = session.createSender("queue://" + getTestName());

        for (int i = 0; i < MSG_COUNT; i++) {
            AmqpMessage message = new AmqpMessage();

            message.setMessageId("msg" + i);
            message.setMessageAnnotation("serialNo", i);
            message.setText("Test-Message");

            sender.send(message);
        }

        sender.close();

        LOG.info("Attempting to read first two messages with receiver #1");
        AmqpReceiver receiver1 = session.createReceiver("queue://" + getTestName());
        receiver1.flow(2);
        AmqpMessage message1 = receiver1.receive(10, TimeUnit.SECONDS);
        AmqpMessage message2 = receiver1.receive(10, TimeUnit.SECONDS);
        assertNotNull("Should have read message 1", message1);
        assertNotNull("Should have read message 2", message2);
        assertEquals("msg0", message1.getMessageId());
        assertEquals("msg1", message2.getMessageId());
        message1.accept();
        message2.accept();

        LOG.info("Attempting to read next two messages with receiver #2");
        AmqpReceiver receiver2 = session.createReceiver("queue://" + getTestName());
        receiver2.flow(2);
        AmqpMessage message3 = receiver2.receive(10, TimeUnit.SECONDS);
        AmqpMessage message4 = receiver2.receive(10, TimeUnit.SECONDS);
        assertNotNull("Should have read message 3", message3);
        assertNotNull("Should have read message 4", message4);
        assertEquals("msg2", message3.getMessageId());
        assertEquals("msg3", message4.getMessageId());
        message3.accept();
        message4.accept();

        LOG.info("Attempting to read remaining messages with receiver #1");
        receiver1.flow(MSG_COUNT - 4);
        for (int i = 4; i < MSG_COUNT; i++) {
            AmqpMessage message = receiver1.receive(10, TimeUnit.SECONDS);
            assertNotNull("Should have read a message", message);
            assertEquals("msg" + i, message.getMessageId());
            message.accept();
        }

        receiver1.close();
        receiver2.close();

        connection.close();
    }

};