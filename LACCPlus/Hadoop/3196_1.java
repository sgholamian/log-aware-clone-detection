//,temp,TestPipelinesFailover.java,276,320,temp,TestPipelinesFailover.java,220,270
//,3
public class xxx {
  @Test(timeout=30000)
  public void testLeaseRecoveryAfterFailover() throws Exception {
    final Configuration conf = new Configuration();
    // Disable permissions so that another user can recover the lease.
    conf.setBoolean(DFSConfigKeys.DFS_PERMISSIONS_ENABLED_KEY, false);
    conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);

    FSDataOutputStream stm = null;
    final MiniDFSCluster cluster = newMiniCluster(conf, 3);
    try {
      cluster.waitActive();
      cluster.transitionToActive(0);
      cluster.setBlockRecoveryTimeout(TimeUnit.SECONDS.toMillis(1));
      Thread.sleep(500);

      LOG.info("Starting with NN 0 active");
      FileSystem fs = HATestUtil.configureFailoverFs(cluster, conf);
      stm = fs.create(TEST_PATH);
      
      // write a block and a half
      AppendTestUtil.write(stm, 0, BLOCK_AND_A_HALF);
      stm.hflush();
      
      LOG.info("Failing over to NN 1");
      
      cluster.transitionToStandby(0);
      cluster.transitionToActive(1);
      
      assertTrue(fs.exists(TEST_PATH));

      FileSystem fsOtherUser = createFsAsOtherUser(cluster, conf);
      loopRecoverLease(fsOtherUser, TEST_PATH);
      
      AppendTestUtil.check(fs, TEST_PATH, BLOCK_AND_A_HALF);
      
      // Fail back to ensure that the block locations weren't lost on the
      // original node.
      cluster.transitionToStandby(1);
      cluster.transitionToActive(0);
      AppendTestUtil.check(fs, TEST_PATH, BLOCK_AND_A_HALF);      
    } finally {
      IOUtils.closeStream(stm);
      cluster.shutdown();
    }
  }

};