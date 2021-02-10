//,temp,TestPipelinesFailover.java,220,270,temp,TestPipelinesFailover.java,142,202
//,3
public class xxx {
  private void doWriteOverFailoverTest(TestScenario scenario,
      MethodToTestIdempotence methodToTest) throws Exception {
    Configuration conf = new Configuration();
    conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
    // Don't check low redundancy periodically.
    conf.setInt(DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY,
        1000);
    
    FSDataOutputStream stm = null;
    MiniDFSCluster cluster = newMiniCluster(conf, 3);
    try {
      int sizeWritten = 0;
      
      cluster.waitActive();
      cluster.transitionToActive(0);
      Thread.sleep(500);

      LOG.info("Starting with NN 0 active");
      FileSystem fs = HATestUtil.configureFailoverFs(cluster, conf);
      stm = fs.create(TEST_PATH);
      
      // write a block and a half
      AppendTestUtil.write(stm, 0, BLOCK_AND_A_HALF);
      sizeWritten += BLOCK_AND_A_HALF;
      
      // Make sure all of the blocks are written out before failover.
      stm.hflush();

      LOG.info("Failing over to another NN");
      int activeIndex = failover(cluster, scenario);

      // NOTE: explicitly do *not* make any further metadata calls
      // to the NN here. The next IPC call should be to allocate the next
      // block. Any other call would notice the failover and not test
      // idempotence of the operation (HDFS-3031)
      
      FSNamesystem ns1 = cluster.getNameNode(activeIndex).getNamesystem();
      BlockManagerTestUtil.updateState(ns1.getBlockManager());
      assertEquals(0, ns1.getPendingReplicationBlocks());
      assertEquals(0, ns1.getCorruptReplicaBlocks());
      assertEquals(0, ns1.getMissingBlocksCount());

      // If we're testing allocateBlock()'s idempotence, write another
      // block and a half, so we have to allocate a new block.
      // Otherise, don't write anything, so our next RPC will be
      // completeFile() if we're testing idempotence of that operation.
      if (methodToTest == MethodToTestIdempotence.ALLOCATE_BLOCK) {
        // write another block and a half
        AppendTestUtil.write(stm, sizeWritten, BLOCK_AND_A_HALF);
        sizeWritten += BLOCK_AND_A_HALF;
      }
      
      stm.close();
      stm = null;
      
      AppendTestUtil.check(fs, TEST_PATH, sizeWritten);
    } finally {
      IOUtils.closeStream(stm);
      cluster.shutdown();
    }
  }

};