//,temp,TestLazyPersistFiles.java,69,83,temp,TestLazyPersistReplicaPlacement.java,156,169
//,3
public class xxx {
  @Test
  public void testRamDiskNotChosenByDefault() throws IOException {
    getClusterBuilder().setStorageTypes(new StorageType[] {RAM_DISK, RAM_DISK})
                       .build();
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path = new Path("/" + METHOD_NAME + ".dat");

    try {
      makeTestFile(path, BLOCK_SIZE, false);
      fail("Block placement to RAM_DISK should have failed without lazyPersist flag");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
    }
  }

};