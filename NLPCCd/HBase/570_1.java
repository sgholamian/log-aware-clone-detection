//,temp,sample_4880.java,2,12,temp,sample_4113.java,2,16
//,3
public class xxx {
public void testCreateSystemNamespace() throws Exception {
final NamespaceDescriptor nsd = UTIL.getAdmin().getNamespaceDescriptor(NamespaceDescriptor.SYSTEM_NAMESPACE.getName());
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
long procId = procExec.submitProcedure( new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("create namespace failed with exception");
}

};