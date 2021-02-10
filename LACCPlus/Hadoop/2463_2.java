//,temp,TestListCorruptFileBlocks.java,371,438,temp,TestListCorruptFileBlocks.java,260,357
//,3
public class xxx {
  @Test (timeout=300000)
  public void testlistCorruptFileBlocks() throws Exception {
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
      DFSTestUtil util = new DFSTestUtil.Builder().
          setName("testGetCorruptFiles").setNumFiles(3).setMaxLevels(1).
          setMaxSize(1024).build();
      util.createFiles(fs, "/corruptData");

      final NameNode namenode = cluster.getNameNode();
      Collection<FSNamesystem.CorruptFileBlockInfo> corruptFileBlocks = 
        namenode.getNamesystem().listCorruptFileBlocks("/corruptData", null);
      int numCorrupt = corruptFileBlocks.size();
      assertTrue(numCorrupt == 0);
      // delete the blocks
      String bpid = cluster.getNamesystem().getBlockPoolId();
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j <= 1; j++) {
          File storageDir = cluster.getInstanceStorageDir(i, j);
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
      }

      int count = 0;
      corruptFileBlocks = namenode.getNamesystem().
        listCorruptFileBlocks("/corruptData", null);
      numCorrupt = corruptFileBlocks.size();
      while (numCorrupt < 3) {
        Thread.sleep(1000);
        corruptFileBlocks = namenode.getNamesystem()
            .listCorruptFileBlocks("/corruptData", null);
        numCorrupt = corruptFileBlocks.size();
        count++;
        if (count > 30)
          break;
      }
      // Validate we get all the corrupt files
      LOG.info("Namenode has bad files. " + numCorrupt);
      assertTrue(numCorrupt == 3);
      // test the paging here

      FSNamesystem.CorruptFileBlockInfo[] cfb = corruptFileBlocks
          .toArray(new FSNamesystem.CorruptFileBlockInfo[0]);
      // now get the 2nd and 3rd file that is corrupt
      String[] cookie = new String[]{"1"};
      Collection<FSNamesystem.CorruptFileBlockInfo> nextCorruptFileBlocks =
        namenode.getNamesystem()
          .listCorruptFileBlocks("/corruptData", cookie);
      FSNamesystem.CorruptFileBlockInfo[] ncfb = nextCorruptFileBlocks
          .toArray(new FSNamesystem.CorruptFileBlockInfo[0]);
      numCorrupt = nextCorruptFileBlocks.size();
      assertTrue(numCorrupt == 2);
      assertTrue(ncfb[0].block.getBlockName()
          .equalsIgnoreCase(cfb[1].block.getBlockName()));

      corruptFileBlocks =
        namenode.getNamesystem()
          .listCorruptFileBlocks("/corruptData", cookie);
      numCorrupt = corruptFileBlocks.size();
      assertTrue(numCorrupt == 0);
      // Do a listing on a dir which doesn't have any corrupt blocks and
      // validate
      util.createFiles(fs, "/goodData");
      corruptFileBlocks = 
        namenode.getNamesystem().listCorruptFileBlocks("/goodData", null);
      numCorrupt = corruptFileBlocks.size();
      assertTrue(numCorrupt == 0);
      util.cleanup(fs, "/corruptData");
      util.cleanup(fs, "/goodData");
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};