//,temp,TestModifyNamespaceProcedure.java,155,176,temp,TestDeleteNamespaceProcedure.java,113,130
//,3
public class xxx {
  @Test
  public void testModifyNamespaceWithInvalidRegionCount() throws Exception {
    final NamespaceDescriptor nsd =
        NamespaceDescriptor.create("testModifyNamespaceWithInvalidRegionCount").build();
    final String nsKey = "hbase.namespace.quota.maxregions";
    final String nsValue = "-1";
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    createNamespaceForTesting(nsd);

    // Modify
    nsd.setConfiguration(nsKey, nsValue);

    long procId = procExec.submitProcedure(
      new ModifyNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Modify namespace failed with exception: " + result.getException());
    assertTrue(ProcedureTestingUtility.getExceptionCause(result) instanceof ConstraintException);
  }

};