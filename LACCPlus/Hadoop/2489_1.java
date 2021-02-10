//,temp,TestStorageMover.java,710,764,temp,TestStorageMover.java,650,705
//,3
public class xxx {
  @Test
  public void testNoSpaceArchive() throws Exception {
    LOG.info("testNoSpaceArchive");
    final PathPolicyMap pathPolicyMap = new PathPolicyMap(0);
    final NamespaceScheme nsScheme = pathPolicyMap.newNamespaceScheme();

    final ClusterScheme clusterScheme = new ClusterScheme(DEFAULT_CONF,
        NUM_DATANODES, REPL, genStorageTypes(NUM_DATANODES), null);
    final MigrationTest test = new MigrationTest(clusterScheme, nsScheme);

    try {
      test.runBasicTest(false);

      // create 2 hot files with replication 3
      final short replication = 3;
      for (int i = 0; i < 2; i++) {
        final Path p = new Path(pathPolicyMap.cold, "file" + i);
        DFSTestUtil.createFile(test.dfs, p, BLOCK_SIZE, replication, 0L);
        waitForAllReplicas(replication, p, test.dfs, 10);
      }

      // set all the ARCHIVE volume to full
      for (DataNode dn : test.cluster.getDataNodes()) {
        setVolumeFull(dn, StorageType.ARCHIVE);
        DataNodeTestUtils.triggerHeartbeat(dn);
      }

      { // test increasing replication but new replicas cannot be created
        // since no more ARCHIVE space.
        final Path file0 = new Path(pathPolicyMap.cold, "file0");
        final Replication r = test.getReplication(file0);
        Assert.assertEquals(0, r.disk);

        final short newReplication = (short) 5;
        test.dfs.setReplication(file0, newReplication);
        waitForAllReplicas(r.archive, file0, test.dfs, 10);

        test.verifyReplication(file0, 0, r.archive);
      }

      { // test creating a hot file
        final Path p = new Path(pathPolicyMap.hot, "foo");
        DFSTestUtil.createFile(test.dfs, p, BLOCK_SIZE, (short) 3, 0L);
      }

      { //test move a cold file to warm
        final Path file1 = new Path(pathPolicyMap.cold, "file1");
        test.dfs.rename(file1, pathPolicyMap.warm);
        test.migrate(ExitStatus.SUCCESS);
        test.verify(true);
      }
    } finally {
      test.shutdownCluster();
    }
  }

};