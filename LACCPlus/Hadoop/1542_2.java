//,temp,TestLazyPersistReplicaRecovery.java,56,74,temp,TestLazyPersistReplicaRecovery.java,31,54
//,3
public class xxx {
  @Test
  public void testDnRestartWithSavedReplicas()
      throws IOException, InterruptedException {

    getClusterBuilder().build();
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path1 = new Path("/" + METHOD_NAME + ".01.dat");

    makeTestFile(path1, BLOCK_SIZE, true);
    ensureFileReplicasOnStorageType(path1, RAM_DISK);

    // Sleep for a short time to allow the lazy writer thread to do its job.
    // However the block replica should not be evicted from RAM_DISK yet.
    Thread.sleep(3 * LAZY_WRITER_INTERVAL_SEC * 1000);
    ensureFileReplicasOnStorageType(path1, RAM_DISK);

    LOG.info("Restarting the DataNode");
    cluster.restartDataNode(0, true);
    cluster.waitActive();
    triggerBlockReport();

    // Ensure that the replica is now on persistent storage.
    ensureFileReplicasOnStorageType(path1, DEFAULT);
  }

};