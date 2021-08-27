//,temp,JMSClientTest.java,839,866,temp,HttpTransportConnectTimeoutTest.java,67,92
//,3
public class xxx {
    @Test(timeout=30000)
    public void testExecptionListenerCalledOnBrokerStop() throws Exception {
        ActiveMQAdmin.enableJMSFrameTracing();

        connection = createConnection();
        Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        final CountDownLatch called = new CountDownLatch(1);

        connection.setExceptionListener(new ExceptionListener() {

            @Override
            public void onException(JMSException exception) {
                LOG.info("Exception listener called: ", exception);
                called.countDown();
            }
        });

        // This makes sure the connection is completely up and connected
        Destination destination = s.createTemporaryQueue();
        MessageProducer producer = s.createProducer(destination);
        assertNotNull(producer);

        stopBroker();

        assertTrue("No exception listener event fired.", called.await(15, TimeUnit.SECONDS));
    }

};