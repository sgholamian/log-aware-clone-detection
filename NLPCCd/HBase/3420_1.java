//,temp,sample_4904.java,2,15,temp,sample_4546.java,2,11
//,3
public class xxx {
public void testDeleteNonEmptyNamespace() throws Exception {
final String namespaceName = "testDeleteNonExistNamespace";
final TableName tableName = TableName.valueOf("testDeleteNonExistNamespace:" + name.getMethodName());
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
createNamespaceForTesting(namespaceName);
MasterProcedureTestingUtility.createTable(procExec, tableName, null, "f1");
long procId = procExec.submitProcedure( new DeleteNamespaceProcedure(procExec.getEnvironment(), namespaceName));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("delete namespace failed with exception");
}

};