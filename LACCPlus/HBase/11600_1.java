//,temp,TestReplicationSyncUpTool.java,352,427,temp,TestReplicationSyncUpTool.java,228,280
//,3
public class xxx {
  private void mimicSyncUpAfterPut() throws Exception {
    LOG.debug("mimicSyncUpAfterPut");
    utility1.restartHBaseCluster(1);
    utility2.shutdownMiniHBaseCluster();

    Put p;
    // another 100 + 1 row to t1_syncup
    // we should see 100 + 2 rows now
    for (int i = 0; i < NB_ROWS_IN_BATCH; i++) {
      p = new Put(Bytes.toBytes("row" + i));
      p.addColumn(famName, qualName, Bytes.toBytes("val" + i));
      ht1Source.put(p);
    }
    p = new Put(Bytes.toBytes("row" + 9998));
    p.addColumn(noRepfamName, qualName, Bytes.toBytes("val" + 9998));
    ht1Source.put(p);

    // another 200 + 1 row to t1_syncup
    // we should see 200 + 2 rows now
    for (int i = 0; i < NB_ROWS_IN_BATCH * 2; i++) {
      p = new Put(Bytes.toBytes("row" + i));
      p.addColumn(famName, qualName, Bytes.toBytes("val" + i));
      ht2Source.put(p);
    }
    p = new Put(Bytes.toBytes("row" + 9998));
    p.addColumn(noRepfamName, qualName, Bytes.toBytes("val" + 9998));
    ht2Source.put(p);

    int rowCount_ht1Source = utility1.countRows(ht1Source);
    assertEquals("t1_syncup has 102 rows on source", 102, rowCount_ht1Source);
    int rowCount_ht2Source = utility1.countRows(ht2Source);
    assertEquals("t2_syncup has 202 rows on source", 202, rowCount_ht2Source);

    utility1.shutdownMiniHBaseCluster();
    utility2.restartHBaseCluster(1);

    Thread.sleep(SLEEP_TIME);

    // before sync up
    int rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
    int rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
    assertEquals("@Peer1 t1_syncup should be NOT sync up and have 50 rows", 50,
      rowCount_ht1TargetAtPeer1);
    assertEquals("@Peer1 t2_syncup should be NOT sync up and have 100 rows", 100,
      rowCount_ht2TargetAtPeer1);

    // after syun up
    for (int i = 0; i < NB_RETRIES; i++) {
      syncUp(utility1);
      rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
      rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
      if (i == NB_RETRIES - 1) {
        if (rowCount_ht1TargetAtPeer1 != 100 || rowCount_ht2TargetAtPeer1 != 200) {
          // syncUP still failed. Let's look at the source in case anything wrong there
          utility1.restartHBaseCluster(1);
          rowCount_ht1Source = utility1.countRows(ht1Source);
          LOG.debug("t1_syncup should have 102 rows at source, and it is " + rowCount_ht1Source);
          rowCount_ht2Source = utility1.countRows(ht2Source);
          LOG.debug("t2_syncup should have 202 rows at source, and it is " + rowCount_ht2Source);
        }
        assertEquals("@Peer1 t1_syncup should be sync up and have 100 rows", 100,
          rowCount_ht1TargetAtPeer1);
        assertEquals("@Peer1 t2_syncup should be sync up and have 200 rows", 200,
          rowCount_ht2TargetAtPeer1);
      }
      if (rowCount_ht1TargetAtPeer1 == 100 && rowCount_ht2TargetAtPeer1 == 200) {
        LOG.info("SyncUpAfterPut succeeded at retry = " + i);
        break;
      } else {
        LOG.debug("SyncUpAfterPut failed at retry = " + i + ", with rowCount_ht1TargetPeer1 ="
            + rowCount_ht1TargetAtPeer1 + " and rowCount_ht2TargetAtPeer1 ="
            + rowCount_ht2TargetAtPeer1);
      }
      Thread.sleep(SLEEP_TIME);
    }
  }

};