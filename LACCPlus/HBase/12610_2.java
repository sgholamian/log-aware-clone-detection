//,temp,TestMasterReplication.java,385,434,temp,TestMasterReplication.java,327,378
//,3
public class xxx {
  @Test
  public void testHFileMultiSlaveReplication() throws Exception {
    LOG.info("testHFileMultiSlaveReplication");
    int numClusters = 3;
    Table[] htables = null;
    try {
      startMiniClusters(numClusters);
      createTableOnClusters(table);

      // Add a slave, 0 -> 1
      addPeer("1", 0, 1);

      htables = getHTablesOnClusters(tableName);

      // Load 100 rows for each hfile range in cluster '0' and validate whether its been replicated
      // to cluster '1'.
      byte[][][] hfileRanges =
          new byte[][][] { new byte[][] { Bytes.toBytes("mmmm"), Bytes.toBytes("oooo") },
              new byte[][] { Bytes.toBytes("ppp"), Bytes.toBytes("rrr") }, };
      int numOfRows = 100;

      int[] expectedCounts =
          new int[] { hfileRanges.length * numOfRows, hfileRanges.length * numOfRows };

      loadAndValidateHFileReplication("testHFileCyclicReplication_0", 0, new int[] { 1 }, row,
        famName, htables, hfileRanges, numOfRows, expectedCounts, true);

      // Validate data is not replicated to cluster '2'.
      assertEquals(0, utilities[2].countRows(htables[2]));

      rollWALAndWait(utilities[0], htables[0].getName(), row);

      // Add one more slave, 0 -> 2
      addPeer("2", 0, 2);

      // Load 200 rows for each hfile range in cluster '0' and validate whether its been replicated
      // to cluster '1' and '2'. Previous data should be replicated to cluster '2'.
      hfileRanges = new byte[][][] { new byte[][] { Bytes.toBytes("ssss"), Bytes.toBytes("uuuu") },
          new byte[][] { Bytes.toBytes("vvv"), Bytes.toBytes("xxx") }, };
      numOfRows = 200;

      int[] newExpectedCounts = new int[] { hfileRanges.length * numOfRows + expectedCounts[0],
          hfileRanges.length * numOfRows + expectedCounts[1], hfileRanges.length * numOfRows };

      loadAndValidateHFileReplication("testHFileCyclicReplication_1", 0, new int[] { 1, 2 }, row,
        famName, htables, hfileRanges, numOfRows, newExpectedCounts, true);

    } finally {
      close(htables);
      shutDownMiniClusters();
    }
  }

};