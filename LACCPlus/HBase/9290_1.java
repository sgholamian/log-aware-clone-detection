//,temp,TestProcedureCleanup.java,119,148,temp,TestProcedureCleanup.java,90,117
//,3
public class xxx {
  @Test
  public void testProcedureUpdatedShouldClean() throws Exception {
    createProcExecutor();
    SuspendProcedure suspendProcedure = new SuspendProcedure();
    long suspendProc = procExecutor.submitProcedure(suspendProcedure);
    LOG.info("Begin to execute " + suspendProc);
    suspendProcedure.latch.countDown();
    Thread.sleep(500);
    LOG.info("begin to restart1 ");
    ProcedureTestingUtility.restart(procExecutor, true);
    LOG.info("finish to restart1 ");
    htu.waitFor(10000, () -> procExecutor.getProcedure(suspendProc) != null);
    // Wait until the suspendProc executed after restart
    suspendProcedure = (SuspendProcedure) procExecutor.getProcedure(suspendProc);
    suspendProcedure.latch.countDown();
    Thread.sleep(500);
    // Should be 1 log since the suspendProcedure is updated in the new log
    assertTrue(procStore.getActiveLogs().size() == 1);
    // restart procExecutor
    LOG.info("begin to restart2");
    // Restart the executor but do not start the workers.
    // Otherwise, the suspendProcedure will soon be executed and the oldest log
    // will be cleaned, leaving only the newest log.
    ProcedureTestingUtility.restart(procExecutor, true, false);
    LOG.info("finish to restart2");
    // There should be two active logs
    assertTrue(procStore.getActiveLogs().size() == 2);
    procExecutor.startWorkers();

  }

};