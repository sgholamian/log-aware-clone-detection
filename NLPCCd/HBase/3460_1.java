//,temp,sample_1190.java,2,13,temp,sample_4881.java,2,15
//,3
public class xxx {
public void testEnableNonDisabledTable() throws Exception {
final TableName tableName = TableName.valueOf(name.getMethodName());
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
MasterProcedureTestingUtility.createTable(procExec, tableName, null, "f1", "f2");
long procId1 = procExec.submitProcedure( new EnableTableProcedure(procExec.getEnvironment(), tableName, false));
ProcedureTestingUtility.waitProcedure(procExec, procId1);
Procedure<?> result = procExec.getResult(procId1);
assertTrue(result.isFailed());


log.info("enable failed with exception");
}

};