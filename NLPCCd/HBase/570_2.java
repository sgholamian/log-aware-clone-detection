//,temp,sample_4880.java,2,12,temp,sample_4113.java,2,16
//,3
public class xxx {
public void dummy_method(){
final NamespaceDescriptor nsd = NamespaceDescriptor.create("testModifyNamespaceWithInvalidRegionCount").build();
final String nsKey = "hbase.namespace.quota.maxregions";
final String nsValue = "-1";
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
createNamespaceForTesting(nsd);
nsd.setConfiguration(nsKey, nsValue);
long procId = procExec.submitProcedure( new ModifyNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("modify namespace failed with exception");
}

};