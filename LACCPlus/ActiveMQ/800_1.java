//,temp,JmsCronSchedulerTest.java,62,74,temp,AMQ3405Test.java,261,278
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                count.incrementAndGet();
                latch.countDown();
                assertTrue(message instanceof TextMessage);
                TextMessage tm = (TextMessage) message;
                try {
                    LOG.info("Received [{}] count: {} ", tm.getText(), count.get());
                } catch (JMSException e) {
                    LOG.error("Unexpected exception in onMessage", e);
                    fail("Unexpected exception in onMessage: " + e.getMessage());
                }
            }

};