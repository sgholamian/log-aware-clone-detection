//,temp,TestCreateNamespaceProcedure.java,164,182,temp,TestCreateNamespaceProcedure.java,101,125
//,3
public class xxx {
  @Test
  public void testCreateSameNamespaceTwice() throws Exception {
    final NamespaceDescriptor nsd =
        NamespaceDescriptor.create("testCreateSameNamespaceTwice").build();
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    long procId1 = procExec.submitProcedure(
      new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId1);
    ProcedureTestingUtility.assertProcNotFailed(procExec, procId1);

    // Create the namespace that exists
    long procId2 = procExec.submitProcedure(
      new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId2);

    // Second create should fail with NamespaceExistException
    Procedure<?> result = procExec.getResult(procId2);
    assertTrue(result.isFailed());
    LOG.debug("Create namespace failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof NamespaceExistException);
  }

};