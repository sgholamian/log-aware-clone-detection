//,temp,TestLazyPersistReplicaRecovery.java,56,74,temp,TestLazyPersistReplicaRecovery.java,31,54
//,3
public class xxx {
  @Test
  public void testDnRestartWithUnsavedReplicas()
      throws IOException, InterruptedException {

    getClusterBuilder().build();
    FsDatasetTestUtil.stopLazyWriter(cluster.getDataNodes().get(0));

    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path1 = new Path("/" + METHOD_NAME + ".01.dat");
    makeTestFile(path1, BLOCK_SIZE, true);
    ensureFileReplicasOnStorageType(path1, RAM_DISK);

    LOG.info("Restarting the DataNode");
    cluster.restartDataNode(0, true);
    cluster.waitActive();

    // Ensure that the replica is still on transient storage.
    ensureFileReplicasOnStorageType(path1, RAM_DISK);
  }

};