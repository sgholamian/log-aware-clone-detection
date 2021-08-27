//,temp,FailoverManagedClusterTest.java,116,125,temp,AMQ4222Test.java,98,108
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        LOG.info("You got a message: " + ((TextMessage) message).getText());
                        countDownLatch.countDown();
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

};