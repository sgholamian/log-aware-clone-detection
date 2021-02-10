//,temp,TestEditLogRace.java,256,320,temp,TestEditLogRace.java,174,223
//,3
public class xxx {
  @Test
  public void testEditLogRolling() throws Exception {
    // start a cluster 
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = null;
    FileSystem fileSys = null;


    AtomicReference<Throwable> caughtErr = new AtomicReference<Throwable>();
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(NUM_DATA_NODES).build();
      cluster.waitActive();
      fileSys = cluster.getFileSystem();
      final NamenodeProtocols nn = cluster.getNameNode().getRpcServer();
      FSImage fsimage = cluster.getNamesystem().getFSImage();
      StorageDirectory sd = fsimage.getStorage().getStorageDir(0);

      startTransactionWorkers(nn, caughtErr);

      long previousLogTxId = 1;

      for (int i = 0; i < NUM_ROLLS && caughtErr.get() == null; i++) {
        try {
          Thread.sleep(20);
        } catch (InterruptedException e) {}

        LOG.info("Starting roll " + i + ".");
        CheckpointSignature sig = nn.rollEditLog();
        
        long nextLog = sig.curSegmentTxId;
        String logFileName = NNStorage.getFinalizedEditsFileName(
            previousLogTxId, nextLog - 1);
        previousLogTxId += verifyEditLogs(cluster.getNamesystem(), fsimage,
          logFileName, previousLogTxId);

        assertEquals(previousLogTxId, nextLog);
        
        File expectedLog = NNStorage.getInProgressEditsFile(sd, previousLogTxId);
        assertTrue("Expect " + expectedLog + " to exist", expectedLog.exists());
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