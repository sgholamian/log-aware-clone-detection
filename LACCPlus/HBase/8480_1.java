//,temp,TestProcedureBypass.java,153,163,temp,TestProcedureBypass.java,98,109
//,3
public class xxx {
  @Test
  public void testBypassingWaitingTimeoutProcedures() throws Exception {
    final WaitingTimeoutProcedure proc = new WaitingTimeoutProcedure();
    long id = procExecutor.submitProcedure(proc);
    Thread.sleep(500);
    // bypass the procedure
    assertTrue(procExecutor.bypassProcedure(id, 1000, true, false));

    htu.waitFor(5000, () -> proc.isSuccess() && proc.isBypass());
    LOG.info("{} finished", proc);
  }

};