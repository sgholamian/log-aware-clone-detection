//,temp,TestEditLogRace.java,451,536,temp,TestEditLogRace.java,351,442
//,3
public class xxx {
  @Test
  public void testSaveRightBeforeSync() throws Exception {
    Configuration conf = getConf();
    NameNode.initMetrics(conf, NamenodeRole.NAMENODE);
    DFSTestUtil.formatNameNode(conf);
    final FSNamesystem namesystem = FSNamesystem.loadFromDisk(conf);

    try {
      FSImage fsimage = namesystem.getFSImage();
      FSEditLog editLog = spy(fsimage.getEditLog());
      DFSTestUtil.setEditLogForTesting(namesystem, editLog);

      final AtomicReference<Throwable> deferredException =
          new AtomicReference<Throwable>();
      final CountDownLatch waitToEnterSync = new CountDownLatch(1);
      
      final Thread doAnEditThread = new Thread() {
        @Override
        public void run() {
          try {
            LOG.info("Starting mkdirs");
            namesystem.mkdirs("/test",
                new PermissionStatus("test","test", new FsPermission((short)00755)),
                true);
            LOG.info("mkdirs complete");
          } catch (Throwable ioe) {
            LOG.fatal("Got exception", ioe);
            deferredException.set(ioe);
            waitToEnterSync.countDown();
          }
        }
      };
      
      Answer<Void> blockingSync = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
          LOG.info("logSync called");
          if (Thread.currentThread() == doAnEditThread) {
            LOG.info("edit thread: Telling main thread we made it just before logSync...");
            waitToEnterSync.countDown();
            LOG.info("edit thread: sleeping for " + BLOCK_TIME + "secs");
            Thread.sleep(BLOCK_TIME*1000);
            LOG.info("Going through to logSync. This will allow the main thread to continue.");
          }
          invocation.callRealMethod();
          LOG.info("logSync complete");
          return null;
        }
      };
      doAnswer(blockingSync).when(editLog).logSync();
      
      doAnEditThread.start();
      LOG.info("Main thread: waiting to just before logSync...");
      waitToEnterSync.await();
      assertNull(deferredException.get());
      LOG.info("Main thread: detected that logSync about to be called.");
      LOG.info("Trying to enter safe mode.");
      LOG.info("This should block for " + BLOCK_TIME + "sec, since we have pending edits");
      
      long st = Time.now();
      namesystem.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
      long et = Time.now();
      LOG.info("Entered safe mode");
      // Make sure we really waited for the flush to complete!
      assertTrue(et - st > (BLOCK_TIME - 1)*1000);

      // Once we're in safe mode, save namespace.
      namesystem.saveNamespace();

      LOG.info("Joining on edit thread...");
      doAnEditThread.join();
      assertNull(deferredException.get());

      // We did 3 edits: begin, txn, and end
      assertEquals(3, verifyEditLogs(namesystem, fsimage,
          NNStorage.getFinalizedEditsFileName(1, 3),
          1));
      // after the save, just the one "begin"
      assertEquals(1, verifyEditLogs(namesystem, fsimage,
          NNStorage.getInProgressEditsFileName(4),
          4));
    } finally {
      LOG.info("Closing nn");
      if(namesystem != null) namesystem.close();
    }
  }  

};