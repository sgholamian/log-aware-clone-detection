//,temp,AMQ2584Test.java,79,131,temp,AMQ2870Test.java,77,131
//,3
public class xxx {
    @Test(timeout = 120000)
    public void testSize() throws Exception {
        int messages = 1000;
        CountDownLatch redeliveryConsumerLatch = new CountDownLatch((messages*3));
        openConsumer(redeliveryConsumerLatch);

        assertEquals(0, broker.getAdminView().getStorePercentUsage());

        for (int i = 0; i < messages; i++) {
            sendMessage(false);
        }

        final BrokerView brokerView = broker.getAdminView();

        broker.getSystemUsage().getStoreUsage().isFull();
        LOG.info("store percent usage: "+brokerView.getStorePercentUsage());
        int storePercentUsage = broker.getAdminView().getStorePercentUsage();
        assertTrue("some store in use", storePercentUsage > minPercentUsageForStore);

        assertTrue("redelivery consumer got all it needs", redeliveryConsumerLatch.await(60, TimeUnit.SECONDS));
        closeConsumer();

        // consume from DLQ
        final CountDownLatch received = new CountDownLatch(messages);
        consumerConnection = (ActiveMQConnection) createConnection();
        Session dlqSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer dlqConsumer = dlqSession.createConsumer(new ActiveMQQueue("ActiveMQ.DLQ"));
        dlqConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (received.getCount() % 500 == 0) {
                    LOG.info("remaining on DLQ: " + received.getCount());
                }
                received.countDown();
            }
        });
        consumerConnection.start();

        assertTrue("Not all messages reached the DLQ", received.await(60, TimeUnit.SECONDS));

        assertTrue("Store usage exceeds expected usage",
                Wait.waitFor(new Wait.Condition() {
                    @Override
                    public boolean isSatisified() throws Exception {
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store precent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }
                }));

         closeConsumer();

    }

};