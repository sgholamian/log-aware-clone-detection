//,temp,TestListCorruptFileBlocks.java,371,438,temp,TestListCorruptFileBlocks.java,260,357
//,3
public class xxx {
  @Test (timeout=300000)
  public void testlistCorruptFileBlocksDFS() throws Exception {
    Configuration conf = new Configuration();
    conf.setLong(DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_KEY, 1000);
    conf.setInt(DFSConfigKeys.DFS_DATANODE_DIRECTORYSCAN_INTERVAL_KEY, 1); // datanode scans
                                                           // directories
    FileSystem fs = null;

    MiniDFSCluster cluster = null;
    try {
      cluster = new MiniDFSCluster.Builder(conf).build();
      cluster.waitActive();
      fs = cluster.getFileSystem();
      DistributedFileSystem dfs = (DistributedFileSystem) fs;
      DFSTestUtil util = new DFSTestUtil.Builder().
          setName("testGetCorruptFiles").setNumFiles(3).
          setMaxLevels(1).setMaxSize(1024).build();
      util.createFiles(fs, "/corruptData");

      RemoteIterator<Path> corruptFileBlocks = 
        dfs.listCorruptFileBlocks(new Path("/corruptData"));
      int numCorrupt = countPaths(corruptFileBlocks);
      assertTrue(numCorrupt == 0);
      // delete the blocks
      String bpid = cluster.getNamesystem().getBlockPoolId();
      // For loop through number of datadirectories per datanode (2)
      for (int i = 0; i < 2; i++) {
        File storageDir = cluster.getInstanceStorageDir(0, i);
        File data_dir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
        List<File> metadataFiles = MiniDFSCluster.getAllBlockMetadataFiles(
            data_dir);
        if (metadataFiles == null)
          continue;
        // assertTrue("Blocks do not exist in data-dir", (blocks != null) &&
        // (blocks.length > 0));
        for (File metadataFile : metadataFiles) {
          File blockFile = Block.metaToBlockFile(metadataFile);
          LOG.info("Deliberately removing file " + blockFile.getName());
          assertTrue("Cannot remove file.", blockFile.delete());
          LOG.info("Deliberately removing file " + metadataFile.getName());
          assertTrue("Cannot remove file.", metadataFile.delete());
          // break;
        }
      }

      int count = 0;
      corruptFileBlocks = dfs.listCorruptFileBlocks(new Path("/corruptData"));
      numCorrupt = countPaths(corruptFileBlocks);
      while (numCorrupt < 3) {
        Thread.sleep(1000);
        corruptFileBlocks = dfs.listCorruptFileBlocks(new Path("/corruptData"));
        numCorrupt = countPaths(corruptFileBlocks);
        count++;
        if (count > 30)
          break;
      }
      // Validate we get all the corrupt files
      LOG.info("Namenode has bad files. " + numCorrupt);
      assertTrue(numCorrupt == 3);

      util.cleanup(fs, "/corruptData");
      util.cleanup(fs, "/goodData");
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};