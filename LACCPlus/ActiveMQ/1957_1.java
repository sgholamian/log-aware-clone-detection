//,temp,AmqpTempDestinationTest.java,340,380,temp,AmqpTempDestinationTest.java,287,328
//,2
public class xxx {
    protected void doTestCreateDynamicReceiverAndSend(boolean topic) throws Exception {
        Source source = createDynamicSource(topic);

        final BrokerViewMBean brokerView = getProxyToBroker();

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpReceiver receiver = session.createReceiver(source);
        assertNotNull(receiver);

        if (topic) {
            assertEquals(1, brokerView.getTemporaryTopics().length);
        } else {
            assertEquals(1, brokerView.getTemporaryQueues().length);
        }

        // Get the new address
        String address = receiver.getReceiver().getRemoteSource().getAddress();
        LOG.info("New dynamic receiver address -> {}", address);

        // Create a message and send to a receive that is listening on the newly
        // created dynamic link address.
        AmqpMessage message = new AmqpMessage();
        message.setMessageId("msg-1");
        message.setText("Test-Message");

        AmqpSender sender = session.createSender(address);
        sender.send(message);

        receiver.flow(1);
        AmqpMessage received = receiver.receive(5, TimeUnit.SECONDS);
        assertNotNull("Should have read a message", received);
        received.accept();

        sender.close();
        receiver.close();

        connection.close();
    }

};