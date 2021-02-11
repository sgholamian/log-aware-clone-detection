//,temp,sample_1190.java,2,13,temp,sample_4879.java,2,15
//,3
public class xxx {
public void testCreateSameNamespaceTwice() throws Exception {
final NamespaceDescriptor nsd = NamespaceDescriptor.create("testCreateSameNamespaceTwice").build();
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
long procId1 = procExec.submitProcedure( new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId1);
ProcedureTestingUtility.assertProcNotFailed(procExec, procId1);
long procId2 = procExec.submitProcedure( new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId2);
Procedure<?> result = procExec.getResult(procId2);
assertTrue(result.isFailed());


log.info("create namespace failed with exception");
}

};