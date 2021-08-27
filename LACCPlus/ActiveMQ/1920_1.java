//,temp,AMQ3274Test.java,550,565,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,655,670
//,2
public class xxx {
        public void waitShutdown(long timeout) {
            CountDownLatch latch;

            try {
                synchronized (this) {
                    latch = shutdownLatch;
                }

                if (latch != null)
                    latch.await(timeout, TimeUnit.MILLISECONDS);
                else
                    LOG.info("echo client shutdown: client does not appear to be active");
            } catch (InterruptedException int_exc) {
                LOG.warn("wait for message client shutdown interrupted", int_exc);
            }
        }

};