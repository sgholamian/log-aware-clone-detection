//,temp,sample_4882.java,2,15,temp,sample_4903.java,2,12
//,3
public class xxx {
public void testCreateNamespaceWithInvalidTableCount() throws Exception {
final NamespaceDescriptor nsd = NamespaceDescriptor.create("testCreateNamespaceWithInvalidTableCount").build();
final String nsKey = "hbase.namespace.quota.maxtables";
final String nsValue = "-1";
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
nsd.setConfiguration(nsKey, nsValue);
long procId = procExec.submitProcedure( new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("create namespace failed with exception");
}

};