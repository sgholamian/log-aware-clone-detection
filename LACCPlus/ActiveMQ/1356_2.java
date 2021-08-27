//,temp,AMQ2584ConcurrentDlqTest.java,146,153,temp,AMQ2584Test.java,107,113
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                if (received.getCount() % 500 == 0) {
                    LOG.info("remaining on DLQ: " + received.getCount());
                }
                received.countDown();
            }

};