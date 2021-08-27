//,temp,MessageEvictionTest.java,153,230,temp,MessageEvictionTest.java,99,151
//,3
public class xxx {
    @Test
    public void testMessageEvictionDiscardedAdvisory() throws Exception {
        setUp(new VMPendingSubscriberMessageStoragePolicy());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final CountDownLatch consumerRegistered = new CountDownLatch(1);
        final CountDownLatch gotAdvisory = new CountDownLatch(1);
        final CountDownLatch advisoryIsGood = new CountDownLatch(1);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ActiveMQTopic discardedAdvisoryDestination =
                        AdvisorySupport.getMessageDiscardedAdvisoryTopic(destination);
                    // use separate session rather than asyncDispatch on consumer session
                    // as we want consumer session to block
                    Session advisorySession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    final MessageConsumer consumer = advisorySession.createConsumer(discardedAdvisoryDestination);
                    consumer.setMessageListener(new MessageListener() {
                        int advisoriesReceived = 0;
                        @Override
                        public void onMessage(Message message) {
                            try {
                                LOG.info("advisory:" + message);
                                ActiveMQMessage activeMQMessage = (ActiveMQMessage) message;
                                assertNotNull(activeMQMessage.getStringProperty(AdvisorySupport.MSG_PROPERTY_CONSUMER_ID));
                                assertEquals(++advisoriesReceived, activeMQMessage.getIntProperty(AdvisorySupport.MSG_PROPERTY_DISCARDED_COUNT));
                                message.acknowledge();
                                advisoryIsGood.countDown();
                            } catch (JMSException e) {
                                e.printStackTrace();
                                fail(e.toString());
                            } finally {
                                gotAdvisory.countDown();
                            }
                        }
                    });
                    consumerRegistered.countDown();
                    gotAdvisory.await(120, TimeUnit.SECONDS);
                    consumer.close();
                    advisorySession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }
        });
        assertTrue("we have an advisory consumer", consumerRegistered.await(60, TimeUnit.SECONDS));
        doTestMessageEvictionMemoryUsage();
        assertTrue("got an advisory for discarded", gotAdvisory.await(0, TimeUnit.SECONDS));
        assertTrue("advisory is good",advisoryIsGood.await(0, TimeUnit.SECONDS));
    }

};