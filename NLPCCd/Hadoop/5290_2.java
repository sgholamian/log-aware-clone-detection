//,temp,sample_5753.java,2,12,temp,sample_1150.java,2,13
//,3
public class xxx {
public void testLazyPersistBlocksAreSaved() throws IOException, InterruptedException, TimeoutException {
getClusterBuilder().build();
final int NUM_BLOCKS = 10;
final String METHOD_NAME = GenericTestUtils.getMethodName();
Path path = new Path("/" + METHOD_NAME + ".dat");
makeTestFile(path, BLOCK_SIZE * NUM_BLOCKS, true);
LocatedBlocks locatedBlocks = ensureFileReplicasOnStorageType(path, RAM_DISK);
waitForMetric("RamDiskBlocksLazyPersisted", NUM_BLOCKS);


log.info("verifying copy was saved to lazypersist");
}

};