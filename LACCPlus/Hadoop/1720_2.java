//,temp,TestLazyPersistFiles.java,69,83,temp,TestLazyWriter.java,40,57
//,3
public class xxx {
  @Test
  public void testLazyPersistBlocksAreSaved()
      throws IOException, InterruptedException, TimeoutException {
    getClusterBuilder().build();
    final int NUM_BLOCKS = 10;
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path = new Path("/" + METHOD_NAME + ".dat");

    // Create a test file
    makeTestFile(path, BLOCK_SIZE * NUM_BLOCKS, true);
    LocatedBlocks locatedBlocks = ensureFileReplicasOnStorageType(path, RAM_DISK);
    waitForMetric("RamDiskBlocksLazyPersisted", NUM_BLOCKS);
    LOG.info("Verifying copy was saved to lazyPersist/");

    // Make sure that there is a saved copy of the replica on persistent
    // storage.
    ensureLazyPersistBlocksAreSaved(locatedBlocks);
  }

};