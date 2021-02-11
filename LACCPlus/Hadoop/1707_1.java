//,temp,LeaseManager.java,425,436,temp,ProtocolHATestBase.java,181,193
//,3
public class xxx {
  void stopMonitor() {
    if (lmthread != null) {
      shouldRunMonitor = false;
      try {
        lmthread.interrupt();
        lmthread.join(3000);
      } catch (InterruptedException ie) {
        LOG.warn("Encountered exception ", ie);
      }
      lmthread = null;
    }
  }

};