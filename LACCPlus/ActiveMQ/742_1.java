//,temp,AMQ3274Test.java,533,548,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,637,653
//,3
public class xxx {
        public void run() {
            CountDownLatch latch;

            try {
                synchronized (this) {
                    latch = shutdownLatch;
                }

                shutdownInd = false;
                processMessages();

                latch.countDown();
            } catch (Exception exc) {
                LOG.error("message client error", exc);
            }
        }

};