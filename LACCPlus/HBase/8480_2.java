//,temp,TestProcedureBypass.java,153,163,temp,TestProcedureBypass.java,98,109
//,3
public class xxx {
  @Test
  public void testStuckProcedure() throws Exception {
    final StuckProcedure proc = new StuckProcedure();
    long id = procExecutor.submitProcedure(proc);
    Thread.sleep(500);
    //bypass the procedure
    assertTrue(procExecutor.bypassProcedure(id, 1000, true, false));
    //Since the procedure is stuck there, we need to restart the executor to recovery.
    ProcedureTestingUtility.restart(procExecutor);
    htu.waitFor(5000, () -> proc.isSuccess() && proc.isBypass());
    LOG.info("{} finished", proc);
  }

};