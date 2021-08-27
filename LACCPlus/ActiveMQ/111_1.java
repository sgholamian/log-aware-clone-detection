//,temp,FailoverPrefetchZeroTest.java,125,137,temp,FailoverConsumerOutstandingCommitTest.java,148,161
//,3
public class xxx {
            public void run() {
                try {
                    LOG.info("receive one...");
                    Message msg = consumer.receive(30000);
                    if (msg != null) {
                        received.add(msg);
                    }
                    receiveDone.countDown();
                    LOG.info("done receive");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

};