//,temp,TestListCorruptFileBlocks.java,134,256,temp,TestListCorruptFileBlocks.java,63,129
//,3
public class xxx {
  @Test (timeout=300000)
  public void testListCorruptFilesCorruptedBlock() throws Exception {
    MiniDFSCluster cluster = null;
    Random random = new Random();
    
    try {
      Configuration conf = new HdfsConfiguration();
      conf.setInt(DFSConfigKeys.DFS_DATANODE_DIRECTORYSCAN_INTERVAL_KEY, 1); // datanode scans directories
      conf.setInt(DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_KEY, 3 * 1000); // datanode sends block reports
      // Set short retry timeouts so this test runs faster
      conf.setInt(HdfsClientConfigKeys.Retry.WINDOW_BASE_KEY, 10);
      cluster = new MiniDFSCluster.Builder(conf).build();
      FileSystem fs = cluster.getFileSystem();

      // create two files with one block each
      DFSTestUtil util = new DFSTestUtil.Builder().
          setName("testCorruptFilesCorruptedBlock").setNumFiles(2).
          setMaxLevels(1).setMaxSize(512).build();
      util.createFiles(fs, "/srcdat10");

      // fetch bad file list from namenode. There should be none.
      final NameNode namenode = cluster.getNameNode();
      Collection<FSNamesystem.CorruptFileBlockInfo> badFiles = namenode.
        getNamesystem().listCorruptFileBlocks("/", null);
      assertTrue("Namenode has " + badFiles.size()
          + " corrupt files. Expecting None.", badFiles.size() == 0);

      // Now deliberately corrupt one block
      String bpid = cluster.getNamesystem().getBlockPoolId();
      File storageDir = cluster.getInstanceStorageDir(0, 1);
      File data_dir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
      assertTrue("data directory does not exist", data_dir.exists());
      List<File> metaFiles = MiniDFSCluster.getAllBlockMetadataFiles(data_dir);
      assertTrue("Data directory does not contain any blocks or there was an "
          + "IO error", metaFiles != null && !metaFiles.isEmpty());
      File metaFile = metaFiles.get(0);
      RandomAccessFile file = new RandomAccessFile(metaFile, "rw");
      FileChannel channel = file.getChannel();
      long position = channel.size() - 2;
      int length = 2;
      byte[] buffer = new byte[length];
      random.nextBytes(buffer);
      channel.write(ByteBuffer.wrap(buffer), position);
      file.close();
      LOG.info("Deliberately corrupting file " + metaFile.getName() +
          " at offset " + position + " length " + length);

      // read all files to trigger detection of corrupted replica
      try {
        util.checkFiles(fs, "/srcdat10");
      } catch (BlockMissingException e) {
        System.out.println("Received BlockMissingException as expected.");
      } catch (IOException e) {
        assertTrue("Corrupted replicas not handled properly. Expecting BlockMissingException " +
            " but received IOException " + e, false);
      }

      // fetch bad file list from namenode. There should be one file.
      badFiles = namenode.getNamesystem().listCorruptFileBlocks("/", null);
      LOG.info("Namenode has bad files. " + badFiles.size());
      assertTrue("Namenode has " + badFiles.size() + " bad files. Expecting 1.",
          badFiles.size() == 1);
      util.cleanup(fs, "/srcdat10");
    } finally {
      if (cluster != null) { cluster.shutdown(); }
    }
  }

};