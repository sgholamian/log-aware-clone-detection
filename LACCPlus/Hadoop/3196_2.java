//,temp,TestPipelinesFailover.java,276,320,temp,TestPipelinesFailover.java,220,270
//,3
public class xxx {
  private void doTestWriteOverFailoverWithDnFail(TestScenario scenario)
      throws Exception {
    Configuration conf = new Configuration();
    conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
    
    FSDataOutputStream stm = null;
    MiniDFSCluster cluster = newMiniCluster(conf, 5);
    try {
      cluster.waitActive();
      cluster.transitionToActive(0);
      Thread.sleep(500);

      LOG.info("Starting with NN 0 active");
      FileSystem fs = HATestUtil.configureFailoverFs(cluster, conf);
      stm = fs.create(TEST_PATH);
      
      // write a block and a half
      AppendTestUtil.write(stm, 0, BLOCK_AND_A_HALF);
      
      // Make sure all the blocks are written before failover
      stm.hflush();

      int nextActive = failover(cluster, scenario);

      assertTrue(fs.exists(TEST_PATH));
      
      cluster.stopDataNode(0);

      // write another block and a half
      AppendTestUtil.write(stm, BLOCK_AND_A_HALF, BLOCK_AND_A_HALF);
      stm.hflush();

      LOG.info("Failing back from NN " + nextActive + " to NN 0");
      cluster.transitionToStandby(nextActive);
      cluster.transitionToActive(0);
      
      cluster.stopDataNode(1);
      
      AppendTestUtil.write(stm, BLOCK_AND_A_HALF*2, BLOCK_AND_A_HALF);
      stm.hflush();
      
      
      stm.close();
      stm = null;
      
      AppendTestUtil.check(fs, TEST_PATH, BLOCK_AND_A_HALF * 3);
    } finally {
      IOUtils.closeStream(stm);
      cluster.shutdown();
    }
  }

};