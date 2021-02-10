//,temp,TestErasureCodingMultipleRacks.java,160,188,temp,TestErasureCodingMultipleRacks.java,123,138
//,3
public class xxx {
  @Test
  public void testSkewedRack3() throws Exception {
    final int dataUnits = ecPolicy.getNumDataUnits();
    final int parityUnits = ecPolicy.getNumParityUnits();
    // Create enough extra DNs on the 2 racks to test even placement.
    // Desired placement is parityUnits replicas on the 2 racks, and 1 replica
    // on the rest of the racks (which only have 1 DN)
    int numRacks = dataUnits - parityUnits + 2;
    setupCluster(dataUnits + parityUnits * 4, numRacks,
        dataUnits - parityUnits);

    final int filesize = ecPolicy.getNumDataUnits() * ecPolicy.getCellSize();
    byte[] contents = new byte[filesize];

    for (int i = 0; i < 10; ++i) {
      final Path path = new Path("/testfile" + i);
      LOG.info("Writing file " + path);
      DFSTestUtil.writeFile(dfs, path, contents);
      ExtendedBlock extendedBlock = DFSTestUtil.getFirstBlock(dfs, path);
      // Wait for replication to finish before testing
      DFSTestUtil.waitForReplication(cluster, extendedBlock, numRacks,
          ecPolicy.getNumDataUnits() + ecPolicy.getNumParityUnits(), 0);
      BlockLocation[] blocks =
          dfs.getFileBlockLocations(path, 0, Long.MAX_VALUE);
      assertEquals(ecPolicy.getNumDataUnits() + ecPolicy.getNumParityUnits(),
          blocks[0].getHosts().length);
      assertRackFailureTolerated(blocks[0].getTopologyPaths());
    }
  }

};