//,temp,LocalDistributedCacheManager.java,250,260,temp,DataStreamer.java,982,993
//,3
public class xxx {
  private void closeResponder() {
    if (response != null) {
      try {
        response.close();
        response.join();
      } catch (InterruptedException  e) {
        LOG.warn("Caught exception", e);
      } finally {
        response = null;
      }
    }
  }

};