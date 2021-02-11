//,temp,sample_5753.java,2,12,temp,sample_5752.java,2,13
//,3
public class xxx {
public void testDnRestartWithSavedReplicas() throws IOException, InterruptedException, TimeoutException {
getClusterBuilder().build();
final String METHOD_NAME = GenericTestUtils.getMethodName();
Path path1 = new Path("/" + METHOD_NAME + ".01.dat");
makeTestFile(path1, BLOCK_SIZE, true);
ensureFileReplicasOnStorageType(path1, RAM_DISK);
Thread.sleep(3 * LAZY_WRITER_INTERVAL_SEC * 1000);
ensureFileReplicasOnStorageType(path1, RAM_DISK);


log.info("restarting the datanode");
}

};