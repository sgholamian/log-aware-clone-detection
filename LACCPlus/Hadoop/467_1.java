//,temp,TestEditLogRace.java,256,320,temp,TestEditLogRace.java,174,223
//,3
public class xxx {
  @Test
  public void testSaveNamespace() throws Exception {
    // start a cluster 
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = null;
    FileSystem fileSys = null;

    AtomicReference<Throwable> caughtErr = new AtomicReference<Throwable>();
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(NUM_DATA_NODES).build();
      cluster.waitActive();
      fileSys = cluster.getFileSystem();
      final FSNamesystem namesystem = cluster.getNamesystem();
      final NamenodeProtocols nn = cluster.getNameNodeRpc();

      FSImage fsimage = namesystem.getFSImage();
      FSEditLog editLog = fsimage.getEditLog();

      startTransactionWorkers(nn, caughtErr);

      for (int i = 0; i < NUM_SAVE_IMAGE && caughtErr.get() == null; i++) {
        try {
          Thread.sleep(20);
        } catch (InterruptedException ignored) {}


        LOG.info("Save " + i + ": entering safe mode");
        namesystem.enterSafeMode(false);

        // Verify edit logs before the save
        // They should start with the first edit after the checkpoint
        long logStartTxId = fsimage.getStorage().getMostRecentCheckpointTxId() + 1; 
        verifyEditLogs(namesystem, fsimage,
            NNStorage.getInProgressEditsFileName(logStartTxId),
            logStartTxId);


        LOG.info("Save " + i + ": saving namespace");
        namesystem.saveNamespace();
        LOG.info("Save " + i + ": leaving safemode");

        long savedImageTxId = fsimage.getStorage().getMostRecentCheckpointTxId();
        
        // Verify that edit logs post save got finalized and aren't corrupt
        verifyEditLogs(namesystem, fsimage,
            NNStorage.getFinalizedEditsFileName(logStartTxId, savedImageTxId),
            logStartTxId);
        
        // The checkpoint id should be 1 less than the last written ID, since
        // the log roll writes the "BEGIN" transaction to the new log.
        assertEquals(fsimage.getStorage().getMostRecentCheckpointTxId(),
                     editLog.getLastWrittenTxId() - 1);

        namesystem.leaveSafeMode();
        LOG.info("Save " + i + ": complete");
      }
    } finally {
      stopTransactionWorkers();
      if (caughtErr.get() != null) {
        throw new RuntimeException(caughtErr.get());
      }
      if(fileSys != null) fileSys.close();
      if(cluster != null) cluster.shutdown();
    }
  }

};