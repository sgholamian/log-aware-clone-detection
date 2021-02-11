//,temp,TestCreateNamespaceProcedure.java,164,182,temp,TestCreateNamespaceProcedure.java,101,125
//,3
public class xxx {
  @Test
  public void testCreateNamespaceWithInvalidTableCount() throws Exception {
    final NamespaceDescriptor nsd =
        NamespaceDescriptor.create("testCreateNamespaceWithInvalidTableCount").build();
    final String nsKey = "hbase.namespace.quota.maxtables";
    final String nsValue = "-1";
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    nsd.setConfiguration(nsKey, nsValue);

    long procId = procExec.submitProcedure(
      new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Create namespace failed with exception: " + result.getException());
    assertTrue(ProcedureTestingUtility.getExceptionCause(result) instanceof ConstraintException);
  }

};