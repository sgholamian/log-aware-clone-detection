//,temp,MapTask.java,2044,2054,temp,MapTask.java,2032,2042
//,3
public class xxx {
  private <INKEY,INVALUE,OUTKEY,OUTVALUE>
  void closeQuietly(RecordReader<INKEY, INVALUE> c) {
    if (c != null) {
      try {
        c.close();
      } catch (IOException ie) {
        // Ignore
        LOG.info("Ignoring exception during close for " + c, ie);
      }
    }
  }

};