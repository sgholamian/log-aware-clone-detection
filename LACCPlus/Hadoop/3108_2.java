//,temp,TestErasureCodingMultipleRacks.java,141,156,temp,TestErasureCodingMultipleRacks.java,123,138
//,3
public class xxx {
  @Test
  public void testSkewedRack1() throws Exception {
    final int dataUnits = ecPolicy.getNumDataUnits();
    final int parityUnits = ecPolicy.getNumParityUnits();
    setupCluster(dataUnits + parityUnits, 2, 1);

    final int filesize = ecPolicy.getNumDataUnits() * ecPolicy.getCellSize();
    byte[] contents = new byte[filesize];

    final Path path = new Path("/testfile");
    LOG.info("Writing file " + path);
    DFSTestUtil.writeFile(dfs, path, contents);
    BlockLocation[] blocks = dfs.getFileBlockLocations(path, 0, Long.MAX_VALUE);
    assertEquals(ecPolicy.getNumDataUnits() + ecPolicy.getNumParityUnits(),
        blocks[0].getHosts().length);
  }

};