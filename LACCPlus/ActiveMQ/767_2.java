//,temp,ExpiredMessagesWithNoConsumerTest.java,395,412,temp,ExpiredMessagesWithNoConsumerTest.java,276,288
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    LOG.info("Got my message: " + message);
                    receivedOneCondition.countDown();
                    waitCondition.await(6, TimeUnit.MINUTES);
                    LOG.info("acking message: " + message);
                    message.acknowledge();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }

};