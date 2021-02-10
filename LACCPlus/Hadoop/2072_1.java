//,temp,TestStandbyBlockManagement.java,52,98,temp,TestStandbyIsHot.java,63,123
//,3
public class xxx {
  @Test(timeout=60000)
  public void testInvalidateBlock() throws Exception {
    Configuration conf = new Configuration();
    HAUtil.setAllowStandbyReads(conf, true);
    conf.setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 1);
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
        .nnTopology(MiniDFSNNTopology.simpleHATopology())
        .numDataNodes(3)
        .build();
    try {
      cluster.waitActive();
      cluster.transitionToActive(0);

      NameNode nn1 = cluster.getNameNode(0);
      NameNode nn2 = cluster.getNameNode(1);

      FileSystem fs = HATestUtil.configureFailoverFs(cluster, conf);

      Thread.sleep(1000);
      LOG.info("==================================");
      DFSTestUtil.writeFile(fs, TEST_FILE_PATH, TEST_FILE_DATA);
      // Have to force an edit log roll so that the standby catches up
      nn1.getRpcServer().rollEditLog();
      LOG.info("==================================");

      // delete the file
      fs.delete(TEST_FILE_PATH, false);
      BlockManagerTestUtil.computeAllPendingWork(
          nn1.getNamesystem().getBlockManager());

      nn1.getRpcServer().rollEditLog();

      // standby nn doesn't need to invalidate blocks.
      assertEquals(0,
          nn2.getNamesystem().getBlockManager().getPendingDeletionBlocksCount());

      cluster.triggerHeartbeats();
      cluster.triggerBlockReports();

      // standby nn doesn't need to invalidate blocks.
      assertEquals(0,
          nn2.getNamesystem().getBlockManager().getPendingDeletionBlocksCount());

    } finally {
      cluster.shutdown();
    }
  }

};