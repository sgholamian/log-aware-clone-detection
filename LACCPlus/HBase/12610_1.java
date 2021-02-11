//,temp,TestMasterReplication.java,385,434,temp,TestMasterReplication.java,327,378
//,3
public class xxx {
  @Test
  public void testHFileReplicationForConfiguredTableCfs() throws Exception {
    LOG.info("testHFileReplicationForConfiguredTableCfs");
    int numClusters = 2;
    Table[] htables = null;
    try {
      startMiniClusters(numClusters);
      createTableOnClusters(table);

      htables = getHTablesOnClusters(tableName);
      // Test the replication scenarios only 'f' is configured for table data replication not 'f1'
      addPeer("1", 0, 1, tableName.getNameAsString() + ":" + Bytes.toString(famName));

      // Load 100 rows for each hfile range in cluster '0' for table CF 'f'
      byte[][][] hfileRanges =
          new byte[][][] { new byte[][] { Bytes.toBytes("aaaa"), Bytes.toBytes("cccc") },
              new byte[][] { Bytes.toBytes("ddd"), Bytes.toBytes("fff") }, };
      int numOfRows = 100;
      int[] expectedCounts =
          new int[] { hfileRanges.length * numOfRows, hfileRanges.length * numOfRows };

      loadAndValidateHFileReplication("load_f", 0, new int[] { 1 }, row, famName, htables,
        hfileRanges, numOfRows, expectedCounts, true);

      // Load 100 rows for each hfile range in cluster '0' for table CF 'f1'
      hfileRanges = new byte[][][] { new byte[][] { Bytes.toBytes("gggg"), Bytes.toBytes("iiii") },
          new byte[][] { Bytes.toBytes("jjj"), Bytes.toBytes("lll") }, };
      numOfRows = 100;

      int[] newExpectedCounts =
          new int[] { hfileRanges.length * numOfRows + expectedCounts[0], expectedCounts[1] };

      loadAndValidateHFileReplication("load_f1", 0, new int[] { 1 }, row, famName1, htables,
        hfileRanges, numOfRows, newExpectedCounts, false);

      // Validate data replication for CF 'f1'

      // Source cluster table should contain data for the families
      wait(0, htables[0], hfileRanges.length * numOfRows + expectedCounts[0]);

      // Sleep for enough time so that the data is still not replicated for the CF which is not
      // configured for replication
      Thread.sleep((NB_RETRIES / 2) * SLEEP_TIME);
      // Peer cluster should have only configured CF data
      wait(1, htables[1], expectedCounts[1]);
    } finally {
      close(htables);
      shutDownMiniClusters();
    }
  }

};