//,temp,TestListCorruptFileBlocks.java,134,256,temp,TestListCorruptFileBlocks.java,63,129
//,3
public class xxx {
  @Test (timeout=300000)
  public void testListCorruptFileBlocksInSafeMode() throws Exception {
    MiniDFSCluster cluster = null;
    Random random = new Random();

    try {
      Configuration conf = new HdfsConfiguration();
      // datanode scans directories
      conf.setInt(DFSConfigKeys.DFS_DATANODE_DIRECTORYSCAN_INTERVAL_KEY, 1);
      // datanode sends block reports
      conf.setInt(DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_KEY, 3 * 1000);
      // never leave safemode automatically
      conf.setFloat(DFSConfigKeys.DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_KEY,
                    1.5f);
      // start populating repl queues immediately 
      conf.setFloat(DFSConfigKeys.DFS_NAMENODE_REPL_QUEUE_THRESHOLD_PCT_KEY,
                    0f);
      // Set short retry timeouts so this test runs faster
      conf.setInt(HdfsClientConfigKeys.Retry.WINDOW_BASE_KEY, 10);
      cluster = new MiniDFSCluster.Builder(conf).waitSafeMode(false).build();
      cluster.getNameNodeRpc().setSafeMode(
          HdfsConstants.SafeModeAction.SAFEMODE_LEAVE, false);
      FileSystem fs = cluster.getFileSystem();

      // create two files with one block each
      DFSTestUtil util = new DFSTestUtil.Builder().
          setName("testListCorruptFileBlocksInSafeMode").setNumFiles(2).
          setMaxLevels(1).setMaxSize(512).build();
      util.createFiles(fs, "/srcdat10");

      // fetch bad file list from namenode. There should be none.
      Collection<FSNamesystem.CorruptFileBlockInfo> badFiles = 
        cluster.getNameNode().getNamesystem().listCorruptFileBlocks("/", null);
      assertTrue("Namenode has " + badFiles.size()
          + " corrupt files. Expecting None.", badFiles.size() == 0);

      // Now deliberately corrupt one block
      File storageDir = cluster.getInstanceStorageDir(0, 0);
      File data_dir = MiniDFSCluster.getFinalizedDir(storageDir, 
          cluster.getNamesystem().getBlockPoolId());
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
        assertTrue("Corrupted replicas not handled properly. " +
                   "Expecting BlockMissingException " +
                   " but received IOException " + e, false);
      }

      // fetch bad file list from namenode. There should be one file.
      badFiles = cluster.getNameNode().getNamesystem().
        listCorruptFileBlocks("/", null);
      LOG.info("Namenode has bad files. " + badFiles.size());
      assertTrue("Namenode has " + badFiles.size() + " bad files. Expecting 1.",
          badFiles.size() == 1);
 
      // restart namenode
      cluster.restartNameNode(0);
      fs = cluster.getFileSystem();

      // wait until replication queues have been initialized
      while (!cluster.getNameNode().namesystem.isPopulatingReplQueues()) {
        try {
          LOG.info("waiting for replication queues");
          Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
      }

      // read all files to trigger detection of corrupted replica
      try {
        util.checkFiles(fs, "/srcdat10");
      } catch (BlockMissingException e) {
        System.out.println("Received BlockMissingException as expected.");
      } catch (IOException e) {
        assertTrue("Corrupted replicas not handled properly. " +
                   "Expecting BlockMissingException " +
                   " but received IOException " + e, false);
      }

      // fetch bad file list from namenode. There should be one file.
      badFiles = cluster.getNameNode().getNamesystem().
        listCorruptFileBlocks("/", null);
      LOG.info("Namenode has bad files. " + badFiles.size());
      assertTrue("Namenode has " + badFiles.size() + " bad files. Expecting 1.",
          badFiles.size() == 1);

      // check that we are still in safe mode
      assertTrue("Namenode is not in safe mode", 
                 cluster.getNameNode().isInSafeMode());

      // now leave safe mode so that we can clean up
      cluster.getNameNodeRpc().setSafeMode(
          HdfsConstants.SafeModeAction.SAFEMODE_LEAVE, false);

      util.cleanup(fs, "/srcdat10");
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw e;
    } finally {
      if (cluster != null) {
        cluster.shutdown(); 
      }
    }
  }

};