//,temp,TestEditLogRace.java,493,568,temp,TestEditLogRace.java,393,484
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
      final FSEditLog editLog = fsimage.getEditLog();

      final AtomicReference<Throwable> deferredException =
          new AtomicReference<Throwable>();
      final CountDownLatch sleepingBeforeSync = new CountDownLatch(1);

      final Thread doAnEditThread = new Thread() {
        @Override
        public void run() {
          try {
            LOG.info("Starting setOwner");
            namesystem.writeLock();
            try {
              editLog.logSetOwner("/","test","test");
            } finally {
              namesystem.writeUnlock();
            }
            sleepingBeforeSync.countDown();
            LOG.info("edit thread: sleeping for " + BLOCK_TIME + "secs");
            Thread.sleep(BLOCK_TIME*1000);
            editLog.logSync();
            LOG.info("edit thread: logSync complete");
          } catch (Throwable ioe) {
            LOG.fatal("Got exception", ioe);
            deferredException.set(ioe);
            sleepingBeforeSync.countDown();
          }
        }
      };
      doAnEditThread.setDaemon(true);
      doAnEditThread.start();
      LOG.info("Main thread: waiting to just before logSync...");
      sleepingBeforeSync.await(200, TimeUnit.MILLISECONDS);
      assertNull(deferredException.get());
      LOG.info("Main thread: detected that logSync about to be called.");
      LOG.info("Trying to enter safe mode.");

      long st = Time.now();
      namesystem.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
      long et = Time.now();
      LOG.info("Entered safe mode after "+(et-st)+"ms");

      // Make sure we didn't wait for the thread that did a logEdit but
      // not logSync.  Going into safemode does a logSyncAll that will flush
      // its edit.
      assertTrue(et - st < (BLOCK_TIME/2)*1000);

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