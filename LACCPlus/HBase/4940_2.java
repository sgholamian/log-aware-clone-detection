//,temp,TestCloneSnapshotProcedure.java,120,136,temp,TestRestoreSnapshotProcedure.java,163,176
//,3
public class xxx {
  @Test
  public void testRestoreSnapshotToDifferentTable() throws Exception {
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
    final TableName restoredTableName = TableName.valueOf(name.getMethodName());
    final HTableDescriptor newHTD = createHTableDescriptor(restoredTableName, CF1, CF2);

    long procId = ProcedureTestingUtility.submitAndWait(
      procExec, new RestoreSnapshotProcedure(procExec.getEnvironment(), newHTD, snapshot));
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Restore snapshot failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof TableNotFoundException);
  }

};