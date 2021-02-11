//,temp,sample_4904.java,2,15,temp,sample_4546.java,2,11
//,3
public class xxx {
public void testTruncateNotExistentTable() throws Exception {
final TableName tableName = TableName.valueOf(name.getMethodName());
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
long procId = ProcedureTestingUtility.submitAndWait(procExec, new TruncateTableProcedure(procExec.getEnvironment(), tableName, true));
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("truncate failed with exception");
}

};