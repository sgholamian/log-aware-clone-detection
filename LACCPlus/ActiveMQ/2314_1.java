//,temp,AmqpSenderTest.java,265,296,temp,AmqpSenderTest.java,216,263
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testPresettledSender() throws Exception {
        final int MSG_COUNT = 1000;

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        AmqpSender sender = session.createSender("topic://" + getTestName(), true);

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
        connection.close();
    }

};