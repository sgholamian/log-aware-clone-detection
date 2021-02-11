//,temp,TestReplicationSyncUpTool.java,352,427,temp,TestReplicationSyncUpToolWithBulkLoadedData.java,105,163
//,3
public class xxx {
  private void mimicSyncUpAfterBulkLoad(Iterator<String> randomHFileRangeListIterator)
      throws Exception {
    LOG.debug("mimicSyncUpAfterBulkLoad");
    utility2.shutdownMiniHBaseCluster();

    loadAndReplicateHFiles(false, randomHFileRangeListIterator);

    int rowCount_ht1Source = utility1.countRows(ht1Source);
    assertEquals("t1_syncup has 206 rows on source, after bulk load of another 103 hfiles", 206,
      rowCount_ht1Source);

    int rowCount_ht2Source = utility1.countRows(ht2Source);
    assertEquals("t2_syncup has 406 rows on source, after bulk load of another 203 hfiles", 406,
      rowCount_ht2Source);

    utility1.shutdownMiniHBaseCluster();
    utility2.restartHBaseCluster(1);

    Thread.sleep(SLEEP_TIME);

    // Before sync up
    int rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
    int rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
    assertEquals("@Peer1 t1_syncup should still have 100 rows", 100, rowCount_ht1TargetAtPeer1);
    assertEquals("@Peer1 t2_syncup should still have 200 rows", 200, rowCount_ht2TargetAtPeer1);

    // Run sync up tool
    syncUp(utility1);

    // After syun up
    for (int i = 0; i < NB_RETRIES; i++) {
      syncUp(utility1);
      rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
      rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
      if (i == NB_RETRIES - 1) {
        if (rowCount_ht1TargetAtPeer1 != 200 || rowCount_ht2TargetAtPeer1 != 400) {
          // syncUP still failed. Let's look at the source in case anything wrong there
          utility1.restartHBaseCluster(1);
          rowCount_ht1Source = utility1.countRows(ht1Source);
          LOG.debug("t1_syncup should have 206 rows at source, and it is " + rowCount_ht1Source);
          rowCount_ht2Source = utility1.countRows(ht2Source);
          LOG.debug("t2_syncup should have 406 rows at source, and it is " + rowCount_ht2Source);
        }
        assertEquals("@Peer1 t1_syncup should be sync up and have 200 rows", 200,
          rowCount_ht1TargetAtPeer1);
        assertEquals("@Peer1 t2_syncup should be sync up and have 400 rows", 400,
          rowCount_ht2TargetAtPeer1);
      }
      if (rowCount_ht1TargetAtPeer1 == 200 && rowCount_ht2TargetAtPeer1 == 400) {
        LOG.info("SyncUpAfterBulkLoad succeeded at retry = " + i);
        break;
      } else {
        LOG.debug("SyncUpAfterBulkLoad failed at retry = " + i + ", with rowCount_ht1TargetPeer1 ="
            + rowCount_ht1TargetAtPeer1 + " and rowCount_ht2TargetAtPeer1 ="
            + rowCount_ht2TargetAtPeer1);
      }
      Thread.sleep(SLEEP_TIME);
    }
  }

};