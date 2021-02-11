//,temp,TestTruncateTableProcedure.java,93,116,temp,TestEnableTableProcedure.java,68,91
//,3
public class xxx {
  @Test(expected = TableNotDisabledException.class)
  public void testEnableNonDisabledTable() throws Exception {
    final TableName tableName = TableName.valueOf(name.getMethodName());
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    MasterProcedureTestingUtility.createTable(procExec, tableName, null, "f1", "f2");

    // Enable the table - expect failure
    long procId1 =
      procExec.submitProcedure(new EnableTableProcedure(procExec.getEnvironment(), tableName));
    ProcedureTestingUtility.waitProcedure(procExec, procId1);

    Procedure<?> result = procExec.getResult(procId1);
    assertTrue(result.isFailed());
    LOG.debug("Enable failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof TableNotDisabledException);

    // Enable the table - expect failure from ProcedurePrepareLatch
    final ProcedurePrepareLatch prepareLatch = new ProcedurePrepareLatch.CompatibilityLatch();
    procExec.submitProcedure(
      new EnableTableProcedure(procExec.getEnvironment(), tableName, prepareLatch));
    prepareLatch.await();
  }

};