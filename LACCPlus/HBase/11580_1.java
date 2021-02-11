//,temp,TestMasterReplication.java,439,471,temp,TestMasterReplication.java,275,321
//,3
public class xxx {
  @Test
  public void testCyclicReplication3() throws Exception {
    LOG.info("testCyclicReplication2");
    int numClusters = 3;
    Table[] htables = null;
    try {
      startMiniClusters(numClusters);
      createTableOnClusters(table);

      // Test the replication scenario of 0 -> 1 -> 2 -> 1
      addPeer("1", 0, 1);
      addPeer("1", 1, 2);
      addPeer("1", 2, 1);

      htables = getHTablesOnClusters(tableName);

      // put "row" and wait 'til it got around
      putAndWait(row, famName, htables[0], htables[2]);
      putAndWait(row1, famName, htables[1], htables[2]);
      putAndWait(row2, famName, htables[2], htables[1]);

      deleteAndWait(row, htables[0], htables[2]);
      deleteAndWait(row1, htables[1], htables[2]);
      deleteAndWait(row2, htables[2], htables[1]);

      int[] expectedCounts = new int[] { 1, 3, 3 };
      validateCounts(htables, put, expectedCounts);
      validateCounts(htables, delete, expectedCounts);
    } finally {
      close(htables);
      shutDownMiniClusters();
    }
  }

};