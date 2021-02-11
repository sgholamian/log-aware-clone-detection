//,temp,TestMasterReplication.java,439,471,temp,TestMasterReplication.java,275,321
//,3
public class xxx {
  @Test
  public void testCyclicReplication2() throws Exception {
    LOG.info("testCyclicReplication2");
    int numClusters = 3;
    Table[] htables = null;
    try {
      startMiniClusters(numClusters);
      createTableOnClusters(table);

      // Test the replication scenario of 0 -> 1 -> 2 -> 0
      addPeer("1", 0, 1);
      addPeer("1", 1, 2);
      addPeer("1", 2, 0);

      htables = getHTablesOnClusters(tableName);

      // put "row" and wait 'til it got around
      putAndWait(row, famName, htables[0], htables[2]);
      putAndWait(row1, famName, htables[1], htables[0]);
      putAndWait(row2, famName, htables[2], htables[1]);

      deleteAndWait(row, htables[0], htables[2]);
      deleteAndWait(row1, htables[1], htables[0]);
      deleteAndWait(row2, htables[2], htables[1]);

      int[] expectedCounts = new int[] { 3, 3, 3 };
      validateCounts(htables, put, expectedCounts);
      validateCounts(htables, delete, expectedCounts);

      // Test HBASE-9158
      disablePeer("1", 2);
      // we now have an edit that was replicated into cluster originating from
      // cluster 0
      putAndWait(row3, famName, htables[0], htables[1]);
      // now add a local edit to cluster 1
      htables[1].put(new Put(row4).addColumn(famName, row4, row4));
      // re-enable replication from cluster 2 to cluster 0
      enablePeer("1", 2);
      // without HBASE-9158 the edit for row4 would have been marked with
      // cluster 0's id
      // and hence not replicated to cluster 0
      wait(row4, htables[0], false);
    } finally {
      close(htables);
      shutDownMiniClusters();
    }
  }

};