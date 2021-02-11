//,temp,TestCachingStrategy.java,295,339,temp,TestCachingStrategy.java,213,248
//,3
public class xxx {
  @Test(timeout=120000)
  public void testFadviseSkippedForSmallReads() throws Exception {
    // start a cluster
    LOG.info("testFadviseSkippedForSmallReads");
    tracker.clear();
    Configuration conf = new HdfsConfiguration();
    conf.setBoolean(DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_READS_KEY, true);
    conf.setBoolean(DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_WRITES_KEY, true);
    MiniDFSCluster cluster = null;
    String TEST_PATH = "/test";
    int TEST_PATH_LEN = MAX_TEST_FILE_LEN;
    FSDataInputStream fis = null;
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1)
          .build();
      cluster.waitActive();
      FileSystem fs = cluster.getFileSystem();

      // create new file
      createHdfsFile(fs, new Path(TEST_PATH), TEST_PATH_LEN, null);
      // Since the DataNode was configured with drop-behind, and we didn't
      // specify any policy, we should have done drop-behind.
      ExtendedBlock block = cluster.getNameNode().getRpcServer().getBlockLocations(
          TEST_PATH, 0, Long.MAX_VALUE).get(0).getBlock();
      String fadvisedFileName = cluster.getBlockFile(0, block).getName();
      Stats stats = tracker.getStats(fadvisedFileName);
      stats.assertDroppedInRange(0, TEST_PATH_LEN - WRITE_PACKET_SIZE);
      stats.clear();
      stats.assertNotDroppedInRange(0, TEST_PATH_LEN);

      // read file
      fis = fs.open(new Path(TEST_PATH));
      byte buf[] = new byte[17];
      fis.readFully(4096, buf, 0, buf.length);

      // we should not have dropped anything because of the small read.
      stats = tracker.getStats(fadvisedFileName);
      stats.assertNotDroppedInRange(0, TEST_PATH_LEN - WRITE_PACKET_SIZE);
    } finally {
      IOUtils.cleanup(null, fis);
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};