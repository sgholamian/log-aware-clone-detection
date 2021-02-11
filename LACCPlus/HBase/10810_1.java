//,temp,TestNamespaceReplication.java,252,270,temp,TestMultiSlaveReplication.java,276,301
//,3
public class xxx {
  private void ensureRowNotExisted(Table target, byte[] row, byte[]... families)
      throws Exception {
    for (byte[] fam : families) {
      Get get = new Get(row);
      get.addFamily(fam);
      for (int i = 0; i < NB_RETRIES; i++) {
        if (i == NB_RETRIES - 1) {
          fail("Waited too much time for delete replication");
        }
        Result res = target.get(get);
        if (res.size() >= 1) {
          LOG.info("Row not deleted");
        } else {
          break;
        }
        Thread.sleep(10 * SLEEP_TIME);
      }
    }
  }

};