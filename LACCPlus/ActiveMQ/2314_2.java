//,temp,AmqpSenderTest.java,265,296,temp,AmqpSenderTest.java,216,263
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testUnsettledSender() throws Exception {
        final int MSG_COUNT = 1000;

        final CountDownLatch settled = new CountDownLatch(MSG_COUNT);

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());

        connection.setStateInspector(new AmqpValidator() {

            @Override
            public void inspectDeliveryUpdate(Sender sender, Delivery delivery) {
                if (delivery.remotelySettled()) {
                    LOG.trace("Remote settled message for sender: {}", sender.getName());
                    settled.countDown();
                }
            }
        });

        AmqpSession session = connection.createSession();
        AmqpSender sender = session.createSender("topic://" + getTestName(), false);

        for (int i = 1; i <= MSG_COUNT; ++i) {
            AmqpMessage message = new AmqpMessage();
            message.setText("Test-Message: " + i);
            sender.send(message);

            if (i % 1000 == 0) {
                LOG.info("Sent message: {}", i);
            }
        }

        final TopicViewMBean topic = getProxyToTopic(getTestName());
        assertTrue("All messages should arrive", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return topic.getEnqueueCount() == MSG_COUNT;
            }
        }));

        sender.close();

        assertTrue("Remote should have settled all deliveries", settled.await(5, TimeUnit.MINUTES));

        connection.close();
    }

};