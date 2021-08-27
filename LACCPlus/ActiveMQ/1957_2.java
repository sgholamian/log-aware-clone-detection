//,temp,AmqpTempDestinationTest.java,340,380,temp,AmqpTempDestinationTest.java,287,328
//,2
public class xxx {
    protected void doTestCreateDynamicSenderAndPublish(boolean topic) throws Exception {
        Target target = createDynamicTarget(topic);

        final BrokerViewMBean brokerView = getProxyToBroker();

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpSender sender = session.createSender(target);
        assertNotNull(sender);

        if (topic) {
            assertEquals(1, brokerView.getTemporaryTopics().length);
        } else {
            assertEquals(1, brokerView.getTemporaryQueues().length);
        }

        // Get the new address
        String address = sender.getSender().getRemoteTarget().getAddress();
        LOG.info("New dynamic sender address -> {}", address);

        // Create a message and send to a receive that is listening on the newly
        // created dynamic link address.
        AmqpMessage message = new AmqpMessage();
        message.setMessageId("msg-1");
        message.setText("Test-Message");

        AmqpReceiver receiver = session.createReceiver(address);
        receiver.flow(1);

        sender.send(message);

        AmqpMessage received = receiver.receive(5, TimeUnit.SECONDS);
        assertNotNull("Should have read a message", received);
        received.accept();

        receiver.close();
        sender.close();

        connection.close();
    }

};