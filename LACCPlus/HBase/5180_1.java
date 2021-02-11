//,temp,TestTruncateTableProcedure.java,93,116,temp,TestCloneSnapshotProcedure.java,120,136
//,3
public class xxx {
  @Test
  public void testTruncateNotDisabledTable() throws Exception {
    final TableName tableName = TableName.valueOf(name.getMethodName());

    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
    MasterProcedureTestingUtility.createTable(procExec, tableName, null, "f");

    // HBASE-20178 has us fail-fast, in the constructor, so add try/catch for this case.
    // Keep old way of looking at procedure too.
    Throwable cause = null;
    try {
      long procId = ProcedureTestingUtility.submitAndWait(procExec,
          new TruncateTableProcedure(procExec.getEnvironment(), tableName, false));

      // Second delete should fail with TableNotDisabled
      Procedure<?> result = procExec.getResult(procId);
      assertTrue(result.isFailed());
      cause = ProcedureTestingUtility.getExceptionCause(result);
    } catch (Throwable t) {
      cause = t;
    }
    LOG.debug("Truncate failed with exception: " + cause);
    assertTrue(cause instanceof TableNotDisabledException);
  }

};