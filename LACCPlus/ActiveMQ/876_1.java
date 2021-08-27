//,temp,AMQ3274Test.java,710,732,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,820,841
//,3
public class xxx {
        public void shutdown() {
            CountDownLatch wait_l;

            synchronized (this) {
                wait_l = waitShutdown;
            }

            Shutdown_ind = true;

            try {
                if (wait_l != null) {
                    if (wait_l.await(3000, TimeUnit.MILLISECONDS)) {
                        LOG.info("echo service shutdown complete");
                    } else {
                        LOG.warn("timeout waiting for echo service shutdown");
                    }
                } else {
                    LOG.info("echo service shutdown: service does not appear to be active");
                }
            } catch (InterruptedException int_exc) {
                LOG.warn("interrupted while waiting for echo service shutdown");
            }
        }

};