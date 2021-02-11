//,temp,TestProcedureCleanup.java,119,148,temp,TestProcedureCleanup.java,90,117
//,3
public class xxx {
  @Test
  public void testProcedureShouldNotCleanOnLoad() throws Exception {
    createProcExecutor();
    final RootProcedure proc = new RootProcedure();
    long rootProc = procExecutor.submitProcedure(proc);
    LOG.info("Begin to execute " + rootProc);
    // wait until the child procedure arrival
    htu.waitFor(10000, () -> procExecutor.getProcedures().size() >= 2);
    SuspendProcedure suspendProcedure = (SuspendProcedure) procExecutor
        .getProcedures().get(1);
    // wait until the suspendProcedure executed
    suspendProcedure.latch.countDown();
    Thread.sleep(100);
    // roll the procedure log
    LOG.info("Begin to roll log ");
    procStore.rollWriterForTesting();
    LOG.info("finish to roll log ");
    Thread.sleep(500);
    LOG.info("begin to restart1 ");
    ProcedureTestingUtility.restart(procExecutor, true);
    LOG.info("finish to restart1 ");
    assertTrue(procExecutor.getProcedure(rootProc) != null);
    Thread.sleep(500);
    LOG.info("begin to restart2 ");
    ProcedureTestingUtility.restart(procExecutor, true);
    LOG.info("finish to restart2 ");
    assertTrue(procExecutor.getProcedure(rootProc) != null);
  }

};