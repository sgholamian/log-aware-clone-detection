//,temp,SessionExpirationTracker.java,95,105,temp,SessionExpirationTracker.java,82,92
//,2
public class xxx {
      @Override
      public void run() {
        try {
          SessionState.setCurrentSessionState(initSessionState);
          runExpirationThread();
        } catch (Exception e) {
          LOG.warn("Exception in TezSessionPool-expiration thread. Thread will shut down", e);
        } finally {
          LOG.info("TezSessionPool-expiration thread exiting");
        }
      }

};