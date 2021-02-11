//,temp,sample_1190.java,2,13,temp,sample_4881.java,2,15
//,3
public class xxx {
public void testCreateNamespaceWithInvalidRegionCount() throws Exception {
final NamespaceDescriptor nsd = NamespaceDescriptor.create("testCreateNamespaceWithInvalidRegionCount").build();
final String nsKey = "hbase.namespace.quota.maxregions";
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