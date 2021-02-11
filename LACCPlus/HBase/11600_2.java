//,temp,TestReplicationSyncUpTool.java,352,427,temp,TestReplicationSyncUpTool.java,228,280
//,3
public class xxx {
  private void putAndReplicateRows() throws Exception {
    LOG.debug("putAndReplicateRows");
    // add rows to Master cluster,
    Put p;

    // 100 + 1 row to t1_syncup
    for (int i = 0; i < NB_ROWS_IN_BATCH; i++) {
      p = new Put(Bytes.toBytes("row" + i));
      p.addColumn(famName, qualName, Bytes.toBytes("val" + i));
      ht1Source.put(p);
    }
    p = new Put(Bytes.toBytes("row" + 9999));
    p.addColumn(noRepfamName, qualName, Bytes.toBytes("val" + 9999));
    ht1Source.put(p);

    // 200 + 1 row to t2_syncup
    for (int i = 0; i < NB_ROWS_IN_BATCH * 2; i++) {
      p = new Put(Bytes.toBytes("row" + i));
      p.addColumn(famName, qualName, Bytes.toBytes("val" + i));
      ht2Source.put(p);
    }
    p = new Put(Bytes.toBytes("row" + 9999));
    p.addColumn(noRepfamName, qualName, Bytes.toBytes("val" + 9999));
    ht2Source.put(p);

    // ensure replication completed
    Thread.sleep(SLEEP_TIME);
    int rowCount_ht1Source = utility1.countRows(ht1Source);
    for (int i = 0; i < NB_RETRIES; i++) {
      int rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
      if (i==NB_RETRIES-1) {
        assertEquals("t1_syncup has 101 rows on source, and 100 on slave1", rowCount_ht1Source - 1,
            rowCount_ht1TargetAtPeer1);
      }
      if (rowCount_ht1Source - 1 == rowCount_ht1TargetAtPeer1) {
        break;
      }
      Thread.sleep(SLEEP_TIME);
    }

    int rowCount_ht2Source = utility1.countRows(ht2Source);
    for (int i = 0; i < NB_RETRIES; i++) {
      int rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
      if (i==NB_RETRIES-1) {
        assertEquals("t2_syncup has 201 rows on source, and 200 on slave1", rowCount_ht2Source - 1,
            rowCount_ht2TargetAtPeer1);
      }
      if (rowCount_ht2Source - 1 == rowCount_ht2TargetAtPeer1) {
        break;
      }
      Thread.sleep(SLEEP_TIME);
    }
  }

};