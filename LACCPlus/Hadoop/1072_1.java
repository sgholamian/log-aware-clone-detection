//,temp,TestListCorruptFileBlocks.java,445,530,temp,TestListCorruptFileBlocks.java,370,437
//,3
public class xxx {
  @Test (timeout=300000)
  public void testMaxCorruptFiles() throws Exception {
    MiniDFSCluster cluster = null;
    try {
      Configuration conf = new HdfsConfiguration();
      conf.setInt(DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_KEY, 3 * 1000); // datanode sends block reports
      cluster = new MiniDFSCluster.Builder(conf).build();
      FileSystem fs = cluster.getFileSystem();
      final int maxCorruptFileBlocks = 
        FSNamesystem.DEFAULT_MAX_CORRUPT_FILEBLOCKS_RETURNED;

      // create 110 files with one block each
      DFSTestUtil util = new DFSTestUtil.Builder().setName("testMaxCorruptFiles").
          setNumFiles(maxCorruptFileBlocks * 3).setMaxLevels(1).setMaxSize(512).
          build();
      util.createFiles(fs, "/srcdat2", (short) 1);
      util.waitReplication(fs, "/srcdat2", (short) 1);

      // verify that there are no bad blocks.
      final NameNode namenode = cluster.getNameNode();
      Collection<FSNamesystem.CorruptFileBlockInfo> badFiles = namenode.
        getNamesystem().listCorruptFileBlocks("/srcdat2", null);
      assertTrue("Namenode has " + badFiles.size() + " corrupt files. Expecting none.",
          badFiles.size() == 0);

      // Now deliberately blocks from all files
      final String bpid = cluster.getNamesystem().getBlockPoolId();
      for (int i=0; i<4; i++) {
        for (int j=0; j<=1; j++) {
          File storageDir = cluster.getInstanceStorageDir(i, j);
          File data_dir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
          LOG.info("Removing files from " + data_dir);
          List<File> metadataFiles = MiniDFSCluster.getAllBlockMetadataFiles(
              data_dir);
          if (metadataFiles == null)
            continue;
          for (File metadataFile : metadataFiles) {
            File blockFile = Block.metaToBlockFile(metadataFile);
            assertTrue("Cannot remove file.", blockFile.delete());
            assertTrue("Cannot remove file.", metadataFile.delete());
          }
        }
      }

      // Run the direcrtoryScanner to update the Datanodes volumeMap
      DataNode dn = cluster.getDataNodes().get(0);
      DataNodeTestUtils.runDirectoryScanner(dn);

      // Occasionally the BlockPoolSliceScanner can run before we have removed
      // the blocks. Restart the Datanode to trigger the scanner into running
      // once more.
      LOG.info("Restarting Datanode to trigger BlockPoolSliceScanner");
      cluster.restartDataNodes();
      cluster.waitActive();

      badFiles = 
        namenode.getNamesystem().listCorruptFileBlocks("/srcdat2", null);
        
       while (badFiles.size() < maxCorruptFileBlocks) {
        LOG.info("# of corrupt files is: " + badFiles.size());
        Thread.sleep(10000);
        badFiles = namenode.getNamesystem().
          listCorruptFileBlocks("/srcdat2", null);
      }
      badFiles = namenode.getNamesystem().
        listCorruptFileBlocks("/srcdat2", null); 
      LOG.info("Namenode has bad files. " + badFiles.size());
      assertTrue("Namenode has " + badFiles.size() + " bad files. Expecting " + 
          maxCorruptFileBlocks + ".",
          badFiles.size() == maxCorruptFileBlocks);

      CorruptFileBlockIterator iter = (CorruptFileBlockIterator)
        fs.listCorruptFileBlocks(new Path("/srcdat2"));
      int corruptPaths = countPaths(iter);
      assertTrue("Expected more than " + maxCorruptFileBlocks +
                 " corrupt file blocks but got " + corruptPaths,
                 corruptPaths > maxCorruptFileBlocks);
      assertTrue("Iterator should have made more than 1 call but made " +
                 iter.getCallsMade(),
                 iter.getCallsMade() > 1);

      util.cleanup(fs, "/srcdat2");
    } finally {
      if (cluster != null) { cluster.shutdown(); }
    }
  }

};