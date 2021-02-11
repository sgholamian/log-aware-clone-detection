//,temp,sample_2811.java,2,12,temp,sample_1845.java,2,13
//,3
public class xxx {
public void testRestoreSnapshotToDifferentTable() throws Exception {
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
final TableName restoredTableName = TableName.valueOf(name.getMethodName());
final HTableDescriptor newHTD = createHTableDescriptor(restoredTableName, CF1, CF2);
long procId = ProcedureTestingUtility.submitAndWait( procExec, new RestoreSnapshotProcedure(procExec.getEnvironment(), newHTD, snapshot));
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("restore snapshot failed with exception");
}

};