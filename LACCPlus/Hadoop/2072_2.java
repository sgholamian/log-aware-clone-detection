//,temp,TestStandbyBlockManagement.java,52,98,temp,TestStandbyIsHot.java,63,123
//,3
public class xxx {
  @Test(timeout=60000)
  public void testStandbyIsHot() throws Exception {
    Configuration conf = new Configuration();
    // We read from the standby to watch block locations
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
      System.err.println("==================================");
      DFSTestUtil.writeFile(fs, TEST_FILE_PATH, TEST_FILE_DATA);
      // Have to force an edit log roll so that the standby catches up
      nn1.getRpcServer().rollEditLog();
      System.err.println("==================================");

      // Block locations should show up on standby.
      LOG.info("Waiting for block locations to appear on standby node");
      waitForBlockLocations(cluster, nn2, TEST_FILE, 3);

      // Trigger immediate heartbeats and block reports so
      // that the active "trusts" all of the DNs
      cluster.triggerHeartbeats();
      cluster.triggerBlockReports();

      // Change replication
      LOG.info("Changing replication to 1");
      fs.setReplication(TEST_FILE_PATH, (short)1);
      BlockManagerTestUtil.computeAllPendingWork(
          nn1.getNamesystem().getBlockManager());
      waitForBlockLocations(cluster, nn1, TEST_FILE, 1);

      nn1.getRpcServer().rollEditLog();
      
      LOG.info("Waiting for lowered replication to show up on standby");
      waitForBlockLocations(cluster, nn2, TEST_FILE, 1);
      
      // Change back to 3
      LOG.info("Changing replication to 3");
      fs.setReplication(TEST_FILE_PATH, (short)3);
      BlockManagerTestUtil.computeAllPendingWork(
          nn1.getNamesystem().getBlockManager());
      nn1.getRpcServer().rollEditLog();
      
      LOG.info("Waiting for higher replication to show up on standby");
      waitForBlockLocations(cluster, nn2, TEST_FILE, 3);
      
    } finally {
      cluster.shutdown();
    }
  }

};