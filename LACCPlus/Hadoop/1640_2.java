//,temp,TestFileAppend4.java,219,293,temp,TestFileAppend4.java,147,211
//,3
public class xxx {
  @Test(timeout=60000)
  public void testRecoverFinalizedBlock() throws Throwable {
    cluster = new MiniDFSCluster.Builder(conf).numDataNodes(5).build();
 
    try {
      cluster.waitActive();
      NamenodeProtocols preSpyNN = cluster.getNameNodeRpc();
      NamenodeProtocols spyNN = spy(preSpyNN);
 
      // Delay completeFile
      GenericTestUtils.DelayAnswer delayer = new GenericTestUtils.DelayAnswer(LOG);
      doAnswer(delayer).when(spyNN).complete(
          anyString(), anyString(), (ExtendedBlock)anyObject(), anyLong());
 
      DFSClient client = new DFSClient(null, spyNN, conf, null);
      file1 = new Path("/testRecoverFinalized");
      final OutputStream stm = client.create("/testRecoverFinalized", true);
 
      // write 1/2 block
      AppendTestUtil.write(stm, 0, 4096);
      final AtomicReference<Throwable> err = new AtomicReference<Throwable>();
      Thread t = new Thread() { 
          @Override
          public void run() {
            try {
              stm.close();
            } catch (Throwable t) {
              err.set(t);
            }
          }};
      t.start();
      LOG.info("Waiting for close to get to latch...");
      delayer.waitForCall();
 
      // At this point, the block is finalized on the DNs, but the file
      // has not been completed in the NN.
      // Lose the leases
      LOG.info("Killing lease checker");
      client.getLeaseRenewer().interruptAndJoin();
 
      FileSystem fs1 = cluster.getFileSystem();
      FileSystem fs2 = AppendTestUtil.createHdfsWithDifferentUsername(
        fs1.getConf());
 
      LOG.info("Recovering file");
      recoverFile(fs2);
 
      LOG.info("Telling close to proceed.");
      delayer.proceed();
      LOG.info("Waiting for close to finish.");
      t.join();
      LOG.info("Close finished.");
 
      // We expect that close will get a "File is not open"
      // error.
      Throwable thrownByClose = err.get();
      assertNotNull(thrownByClose);
      assertTrue(thrownByClose instanceof IOException);
      if (!thrownByClose.getMessage().contains(
            "No lease on /testRecoverFinalized"))
        throw thrownByClose;
    } finally {
      cluster.shutdown();
    }
  }

};