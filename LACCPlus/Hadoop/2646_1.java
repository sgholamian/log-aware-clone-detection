//,temp,MapTask.java,2044,2054,temp,MapTask.java,2032,2042
//,3
public class xxx {
  private <OUTKEY, OUTVALUE>
  void closeQuietly(MapOutputCollector<OUTKEY, OUTVALUE> c) {
    if (c != null) {
      try {
        c.close();
      } catch (Exception ie) {
        // Ignore
        LOG.info("Ignoring exception during close for " + c, ie);
      }
    }
  }

};