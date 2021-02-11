//,temp,TestPipelinesFailover.java,269,315,temp,TestHAStateTransitions.java,289,334
//,3
public class xxx {
  @Test(timeout=120000)
  public void testLeasesRenewedOnTransition() throws Exception {
    Configuration conf = new Configuration();
    conf.setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 1);
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
      .nnTopology(MiniDFSNNTopology.simpleHATopology())
      .numDataNodes(1)
      .build();
    FSDataOutputStream stm = null;
    FileSystem fs = HATestUtil.configureFailoverFs(cluster, conf);
    NameNode nn0 = cluster.getNameNode(0);
    NameNode nn1 = cluster.getNameNode(1);

    try {
      cluster.waitActive();
      cluster.transitionToActive(0);
      
      LOG.info("Starting with NN 0 active");

      stm = fs.create(TEST_FILE_PATH);
      long nn0t0 = NameNodeAdapter.getLeaseRenewalTime(nn0, TEST_FILE_STR);
      assertTrue(nn0t0 > 0);
      long nn1t0 = NameNodeAdapter.getLeaseRenewalTime(nn1, TEST_FILE_STR);
      assertEquals("Lease should not yet exist on nn1",
          -1, nn1t0);
      
      Thread.sleep(5); // make sure time advances!

      HATestUtil.waitForStandbyToCatchUp(nn0, nn1);
      long nn1t1 = NameNodeAdapter.getLeaseRenewalTime(nn1, TEST_FILE_STR);
      assertTrue("Lease should have been created on standby. Time was: " +
          nn1t1, nn1t1 > nn0t0);
          
      Thread.sleep(5); // make sure time advances!
      
      LOG.info("Failing over to NN 1");
      cluster.transitionToStandby(0);
      cluster.transitionToActive(1);
      long nn1t2 = NameNodeAdapter.getLeaseRenewalTime(nn1, TEST_FILE_STR);
      assertTrue("Lease should have been renewed by failover process",
          nn1t2 > nn1t1);
    } finally {
      IOUtils.closeStream(stm);
      cluster.shutdown();
    }
  }

};