//,temp,ExpiredMessagesWithNoConsumerTest.java,395,412,temp,ExpiredMessagesWithNoConsumerTest.java,276,288
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    if(LOG.isDebugEnabled()) {
                        LOG.debug("Got my message: " + message);
                    }
                    receivedOneCondition.countDown();
                    received.incrementAndGet();
                    waitCondition.await(5, TimeUnit.MINUTES);
                    if(LOG.isDebugEnabled()) {
                        LOG.debug("acking message: " + message);
                    }
                    message.acknowledge();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }

};