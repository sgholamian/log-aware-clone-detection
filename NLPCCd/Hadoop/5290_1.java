//,temp,sample_5753.java,2,12,temp,sample_1150.java,2,13
//,3
public class xxx {
public void testDnRestartWithUnsavedReplicas() throws IOException, InterruptedException, TimeoutException {
getClusterBuilder().build();
FsDatasetTestUtil.stopLazyWriter(cluster.getDataNodes().get(0));
final String METHOD_NAME = GenericTestUtils.getMethodName();
Path path1 = new Path("/" + METHOD_NAME + ".01.dat");
makeTestFile(path1, BLOCK_SIZE, true);
ensureFileReplicasOnStorageType(path1, RAM_DISK);


log.info("restarting the datanode");
}

};