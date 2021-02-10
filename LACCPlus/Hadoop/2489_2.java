//,temp,TestStorageMover.java,710,764,temp,TestStorageMover.java,650,705
//,3
public class xxx {
  @Test
  public void testNoSpaceDisk() throws Exception {
    LOG.info("testNoSpaceDisk");
    final PathPolicyMap pathPolicyMap = new PathPolicyMap(0);
    final NamespaceScheme nsScheme = pathPolicyMap.newNamespaceScheme();

    Configuration conf = new Configuration(DEFAULT_CONF);
    final ClusterScheme clusterScheme = new ClusterScheme(conf,
        NUM_DATANODES, REPL, genStorageTypes(NUM_DATANODES), null);
    final MigrationTest test = new MigrationTest(clusterScheme, nsScheme);

    try {
      test.runBasicTest(false);

      // create 2 hot files with replication 3
      final short replication = 3;
      for (int i = 0; i < 2; i++) {
        final Path p = new Path(pathPolicyMap.hot, "file" + i);
        DFSTestUtil.createFile(test.dfs, p, BLOCK_SIZE, replication, 0L);
        waitForAllReplicas(replication, p, test.dfs, 10);
      }

      // set all the DISK volume to full
      for (DataNode dn : test.cluster.getDataNodes()) {
        setVolumeFull(dn, StorageType.DISK);
        DataNodeTestUtils.triggerHeartbeat(dn);
      }

      // test increasing replication.  Since DISK is full,
      // new replicas should be stored in ARCHIVE as a fallback storage.
      final Path file0 = new Path(pathPolicyMap.hot, "file0");
      final Replication r = test.getReplication(file0);
      final short newReplication = (short) 5;
      test.dfs.setReplication(file0, newReplication);
      waitForAllReplicas(newReplication, file0, test.dfs, 10);
      test.verifyReplication(file0, r.disk, newReplication - r.disk);

      // test creating a cold file and then increase replication
      final Path p = new Path(pathPolicyMap.cold, "foo");
      DFSTestUtil.createFile(test.dfs, p, BLOCK_SIZE, replication, 0L);
      waitForAllReplicas(replication, p, test.dfs, 10);
      test.verifyReplication(p, 0, replication);

      test.dfs.setReplication(p, newReplication);
      waitForAllReplicas(newReplication, p, test.dfs, 10);
      test.verifyReplication(p, 0, newReplication);

      //test move a hot file to warm
      final Path file1 = new Path(pathPolicyMap.hot, "file1");
      test.dfs.rename(file1, pathPolicyMap.warm);
      test.migrate(ExitStatus.NO_MOVE_BLOCK);
      test.verifyFile(new Path(pathPolicyMap.warm, "file1"), WARM.getId());
    } finally {
      test.shutdownCluster();
    }
  }

};