//,temp,JMSClientTest.java,839,866,temp,HttpTransportConnectTimeoutTest.java,67,92
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSendReceiveAfterPause() throws Exception {
        final CountDownLatch failed = new CountDownLatch(1);

        Connection connection = factory.createConnection();
        connection.start();
        connection.setExceptionListener(new ExceptionListener() {

            @Override
            public void onException(JMSException exception) {
                LOG.info("Connection failed due to: {}", exception.getMessage());
                failed.countDown();
            }
        });

        assertFalse(failed.await(3, TimeUnit.SECONDS));

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createTemporaryQueue();
        MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(queue);

        producer.send(session.createMessage());

        assertNotNull(consumer.receive(5000));
    }

};