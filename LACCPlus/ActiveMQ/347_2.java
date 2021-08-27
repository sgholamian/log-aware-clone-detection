//,temp,TopicBridgeSelectorConduitOnOff.java,107,134,temp,AMQ2584ConcurrentDlqTest.java,140,156
//,3
public class xxx {
    private void openDlqConsumer(final CountDownLatch received) throws Exception {

        dlqConnection = (ActiveMQConnection) createConnection();
        Session dlqSession = dlqConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer dlqConsumer = dlqSession.createConsumer(new ActiveMQQueue("ActiveMQ.DLQ"));
        dlqConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (received.getCount() > 0 && received.getCount() % 200 == 0) {
                    LOG.info("remaining on DLQ: " + received.getCount());
                }
                received.countDown();
                dlqConsumerLastReceivedTimeStamp = System.currentTimeMillis();
                dlqReceivedCount.incrementAndGet();
            }
        });
        dlqConnection.start();
    }

};