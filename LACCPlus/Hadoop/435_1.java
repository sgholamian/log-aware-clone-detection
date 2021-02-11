//,temp,TestCachingStrategy.java,254,293,temp,TestCachingStrategy.java,213,248
//,3
public class xxx {
  @Test(timeout=120000)
  public void testClientDefaults() throws Exception {
    // start a cluster
    LOG.info("testClientDefaults");
    tracker.clear();
    Configuration conf = new HdfsConfiguration();
    conf.setBoolean(DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_READS_KEY, false);
    conf.setBoolean(DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_WRITES_KEY, false);
    conf.setBoolean(DFSConfigKeys.DFS_CLIENT_CACHE_DROP_BEHIND_READS, true);
    conf.setBoolean(DFSConfigKeys.DFS_CLIENT_CACHE_DROP_BEHIND_WRITES, true);
    MiniDFSCluster cluster = null;
    String TEST_PATH = "/test";
    int TEST_PATH_LEN = MAX_TEST_FILE_LEN;
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1)
          .build();
      cluster.waitActive();
      FileSystem fs = cluster.getFileSystem();
      
      // create new file
      createHdfsFile(fs, new Path(TEST_PATH), TEST_PATH_LEN, null);
      // verify that we dropped everything from the cache during file creation.
      ExtendedBlock block = cluster.getNameNode().getRpcServer().getBlockLocations(
          TEST_PATH, 0, Long.MAX_VALUE).get(0).getBlock();
      String fadvisedFileName = cluster.getBlockFile(0, block).getName();
      Stats stats = tracker.getStats(fadvisedFileName);
      stats.assertDroppedInRange(0, TEST_PATH_LEN - WRITE_PACKET_SIZE);
      stats.clear();
      
      // read file
      readHdfsFile(fs, new Path(TEST_PATH), Long.MAX_VALUE, null);
      // verify that we dropped everything from the cache.
      Assert.assertNotNull(stats);
      stats.assertDroppedInRange(0, TEST_PATH_LEN - WRITE_PACKET_SIZE);
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};