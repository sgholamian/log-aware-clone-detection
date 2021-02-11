//,temp,TestEditLogRace.java,493,568,temp,TestEditLogRace.java,393,484
//,3
public class xxx {
  @Test
  public void testSaveImageWhileSyncInProgress() throws Exception {
    Configuration conf = getConf();
    NameNode.initMetrics(conf, NamenodeRole.NAMENODE);
    DFSTestUtil.formatNameNode(conf);
    final FSNamesystem namesystem = FSNamesystem.loadFromDisk(conf);

    try {
      FSImage fsimage = namesystem.getFSImage();
      FSEditLog editLog = fsimage.getEditLog();

      JournalAndStream jas = editLog.getJournals().get(0);
      EditLogFileOutputStream spyElos =
          spy((EditLogFileOutputStream)jas.getCurrentStream());
      jas.setCurrentStreamForTests(spyElos);

      final AtomicReference<Throwable> deferredException =
          new AtomicReference<Throwable>();
      final CountDownLatch waitToEnterFlush = new CountDownLatch(1);
      
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
            waitToEnterFlush.countDown();
          }
        }
      };
      
      Answer<Void> blockingFlush = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
          LOG.info("Flush called");
          if (useAsyncEditLog || Thread.currentThread() == doAnEditThread) {
            LOG.info("edit thread: Telling main thread we made it to flush section...");
            // Signal to main thread that the edit thread is in the racy section
            waitToEnterFlush.countDown();
            LOG.info("edit thread: sleeping for " + BLOCK_TIME + "secs");
            Thread.sleep(BLOCK_TIME*1000);
            LOG.info("Going through to flush. This will allow the main thread to continue.");
          }
          invocation.callRealMethod();
          LOG.info("Flush complete");
          return null;
        }
      };
      doAnswer(blockingFlush).when(spyElos).flush();
      
      doAnEditThread.start();
      // Wait for the edit thread to get to the logsync unsynchronized section
      LOG.info("Main thread: waiting to enter flush...");
      waitToEnterFlush.await();
      assertNull(deferredException.get());
      LOG.info("Main thread: detected that logSync is in unsynchronized section.");
      LOG.info("Trying to enter safe mode.");
      LOG.info("This should block for " + BLOCK_TIME + "sec, since flush will sleep that long");
      
      long st = Time.now();
      namesystem.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
      long et = Time.now();
      LOG.info("Entered safe mode");
      // Make sure we really waited for the flush to complete!
      assertTrue(et - st > (BLOCK_TIME - 1)*1000);

      // Once we're in safe mode, save namespace.
      namesystem.saveNamespace(0, 0);

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