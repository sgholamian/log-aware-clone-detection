//,temp,sample_4445.java,2,17,temp,sample_4902.java,2,13
//,3
public class xxx {
public void testDeleteNonExistNamespace() throws Exception {
final String namespaceName = "testDeleteNonExistNamespace";
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
validateNamespaceNotExist(namespaceName);
long procId = procExec.submitProcedure( new DeleteNamespaceProcedure(procExec.getEnvironment(), namespaceName));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("delete namespace failed with exception");
}

};