//,temp,TestCloneSnapshotProcedure.java,120,136,temp,TestRestoreSnapshotProcedure.java,163,176
//,3
public class xxx {
  @Test
  public void testCloneSnapshotToSameTable() throws Exception {
    // take the snapshot
    SnapshotProtos.SnapshotDescription snapshotDesc = getSnapshot();

    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
    final TableName clonedTableName = TableName.valueOf(snapshotDesc.getTable());
    final TableDescriptor htd = createTableDescriptor(clonedTableName, CF);

    long procId = ProcedureTestingUtility.submitAndWait(
      procExec, new CloneSnapshotProcedure(procExec.getEnvironment(), htd, snapshotDesc));
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Clone snapshot failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof TableExistsException);
  }

};