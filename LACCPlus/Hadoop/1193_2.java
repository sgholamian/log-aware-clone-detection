//,temp,MapTask.java,1989,1999,temp,ReduceTask.java,633,642
//,3
public class xxx {
  private <OUTKEY, OUTVALUE>
  void closeQuietly(RecordWriter<OUTKEY, OUTVALUE> c, Reporter r) {
    if (c != null) {
      try {
        c.close(r);
      } catch (Exception e) {
        LOG.info("Exception in closing " + c, e);
      }
    }
  }

};