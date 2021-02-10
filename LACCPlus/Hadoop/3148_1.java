//,temp,TestCachingStrategy.java,342,373,temp,TestCachingStrategy.java,214,249
//,3
public class xxx {
  @Test(timeout=120000)
  public void testNoFadviseAfterWriteThenRead() throws Exception {
    // start a cluster
    LOG.info("testNoFadviseAfterWriteThenRead");
    tracker.clear();
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = null;
    String TEST_PATH = "/test";
    int TEST_PATH_LEN = MAX_TEST_FILE_LEN;
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1)
          .build();
      cluster.waitActive();
      FileSystem fs = cluster.getFileSystem();

      // create new file
      createHdfsFile(fs, new Path(TEST_PATH), TEST_PATH_LEN, false);
      // verify that we did not drop everything from the cache during file creation.
      ExtendedBlock block = cluster.getNameNode().getRpcServer().getBlockLocations(
          TEST_PATH, 0, Long.MAX_VALUE).get(0).getBlock();
      String fadvisedFileName = cluster.getBlockFile(0, block).getName();
      Stats stats = tracker.getStats(fadvisedFileName);
      Assert.assertNull(stats);
      
      // read file
      readHdfsFile(fs, new Path(TEST_PATH), Long.MAX_VALUE, false);
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};