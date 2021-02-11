//,temp,TestProcedureBypass.java,153,163,temp,TestProcedureBypass.java,87,96
//,3
public class xxx {
  @Test
  public void testBypassSuspendProcedure() throws Exception {
    final SuspendProcedure proc = new SuspendProcedure();
    long id = procExecutor.submitProcedure(proc);
    Thread.sleep(500);
    //bypass the procedure
    assertTrue(procExecutor.bypassProcedure(id, 30000, false, false));
    htu.waitFor(5000, () -> proc.isSuccess() && proc.isBypass());
    LOG.info("{} finished", proc);
  }

};