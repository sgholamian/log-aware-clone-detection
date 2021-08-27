//,temp,AMQ2584ConcurrentDlqTest.java,146,153,temp,AMQ2584Test.java,107,113
//,3
public class xxx {
            public void onMessage(Message message) {
                if (received.getCount() > 0 && received.getCount() % 200 == 0) {
                    LOG.info("remaining on DLQ: " + received.getCount());
                }
                received.countDown();
                dlqConsumerLastReceivedTimeStamp = System.currentTimeMillis();
                dlqReceivedCount.incrementAndGet();
            }

};