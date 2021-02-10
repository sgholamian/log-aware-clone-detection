//,temp,MapTask.java,2001,2012,temp,ReduceTask.java,633,642
//,3
public class xxx {
  private <INKEY, INVALUE, OUTKEY, OUTVALUE>
  void closeQuietly(
      org.apache.hadoop.mapreduce.RecordReader<INKEY, INVALUE> c) {
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