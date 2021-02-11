//,temp,sample_4882.java,2,15,temp,sample_4903.java,2,12
//,3
public class xxx {
public void testDeleteSystemNamespace() throws Exception {
final String namespaceName = NamespaceDescriptor.SYSTEM_NAMESPACE.getName();
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
long procId = procExec.submitProcedure( new DeleteNamespaceProcedure(procExec.getEnvironment(), namespaceName));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("delete namespace failed with exception");
}

};