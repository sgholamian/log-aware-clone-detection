//,temp,AmqpSendReceiveTest.java,710,787,temp,AmqpSendReceiveTest.java,199,259
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTwoPresettledReceiversReceiveAllMessages() throws Exception {
        final int MSG_COUNT = 100;

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        final String address = "queue://" + getTestName();

        AmqpSender sender = session.createSender(address);
        AmqpReceiver receiver1 = session.createReceiver(address, null, false, true);
        AmqpReceiver receiver2 = session.createReceiver(address, null, false, true);

        for (int i = 0; i < MSG_COUNT; i++) {
            AmqpMessage message = new AmqpMessage();
            message.setMessageId("msg" + i);
            sender.send(message);
        }

        final DestinationViewMBean destinationView = getProxyToQueue(getTestName());

        LOG.info("Attempting to read first two messages with receiver #1");
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
        receiver2.flow(2);
        AmqpMessage message3 = receiver2.receive(10, TimeUnit.SECONDS);
        AmqpMessage message4 = receiver2.receive(10, TimeUnit.SECONDS);
        assertNotNull("Should have read message 3", message3);
        assertNotNull("Should have read message 4", message4);
        assertEquals("msg2", message3.getMessageId());
        assertEquals("msg3", message4.getMessageId());
        message3.accept();
        message4.accept();

        assertTrue("Should be no inflight messages: " + destinationView.getInFlightCount(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return destinationView.getInFlightCount() == 0;
            }
        }));

        LOG.info("*** Attempting to read remaining messages with both receivers");
        int splitCredit = (MSG_COUNT - 4) / 2;

        LOG.info("**** Receiver #1 granting credit[{}] for its block of messages", splitCredit);
        receiver1.flow(splitCredit);
        for (int i = 0; i < splitCredit; i++) {
            AmqpMessage message = receiver1.receive(10, TimeUnit.SECONDS);
            assertNotNull("Receiver #1 should have read a message", message);
            LOG.info("Receiver #1 read message: {}", message.getMessageId());
            message.accept();
        }

        LOG.info("**** Receiver #2 granting credit[{}] for its block of messages", splitCredit);
        receiver2.flow(splitCredit);
        for (int i = 0; i < splitCredit; i++) {
            AmqpMessage message = receiver2.receive(10, TimeUnit.SECONDS);
            assertNotNull("Receiver #2 should have read a message[" + i + "]", message);
            LOG.info("Receiver #2 read message: {}", message.getMessageId());
            message.accept();
        }

        receiver1.close();
        receiver2.close();

        connection.close();
    }

};