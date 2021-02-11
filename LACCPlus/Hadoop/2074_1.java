//,temp,TestPipelinesFailover.java,209,263,temp,TestPipelinesFailover.java,129,191
//,3
public class xxx {
  private void doTestWriteOverFailoverWithDnFail(TestScenario scenario)
      throws Exception {
    Configuration conf = new Configuration();
    conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
    
    FSDataOutputStream stm = null;
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
      .nnTopology(MiniDFSNNTopology.simpleHATopology())
      .numDataNodes(5)
      .build();
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

      LOG.info("Failing over to NN 1");
      scenario.run(cluster);

      assertTrue(fs.exists(TEST_PATH));
      
      cluster.stopDataNode(0);

      // write another block and a half
      AppendTestUtil.write(stm, BLOCK_AND_A_HALF, BLOCK_AND_A_HALF);
      stm.hflush();
      
      LOG.info("Failing back to NN 0");
      cluster.transitionToStandby(1);
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