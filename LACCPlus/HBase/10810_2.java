//,temp,TestNamespaceReplication.java,252,270,temp,TestMultiSlaveReplication.java,276,301
//,3
public class xxx {
  private void deleteAndWait(byte[] row, Table source, Table... targets)
  throws Exception {
    Delete del = new Delete(row);
    source.delete(del);

    Get get = new Get(row);
    for (int i = 0; i < NB_RETRIES; i++) {
      if (i==NB_RETRIES-1) {
        fail("Waited too much time for del replication");
      }
      boolean removedFromAll = true;
      for (Table target : targets) {
        Result res = target.get(get);
        if (res.size() >= 1) {
          LOG.info("Row not deleted");
          removedFromAll = false;
          break;
        }
      }
      if (removedFromAll) {
        break;
      } else {
        Thread.sleep(SLEEP_TIME);
      }
    }
  }

};