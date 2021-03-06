//,temp,SystemServiceManagerImpl.java,135,148,temp,EventFetcher.java,98,106
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    LOG.info("Stopping {}", getName());
    stopExecutors.set(true);

    if (serviceLaucher != null) {
      serviceLaucher.interrupt();
      try {
        serviceLaucher.join();
      } catch (InterruptedException ie) {
        LOG.warn("Interrupted Exception while stopping", ie);
      }
    }
  }

};