//,temp,TestDeleteNamespaceProcedure.java,132,145,temp,TestDeleteNamespaceProcedure.java,113,130
//,3
public class xxx {
  @Test
  public void testDeleteSystemNamespace() throws Exception {
    final String namespaceName = NamespaceDescriptor.SYSTEM_NAMESPACE.getName();
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    long procId = procExec.submitProcedure(
      new DeleteNamespaceProcedure(procExec.getEnvironment(), namespaceName));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Delete namespace failed with exception: " + result.getException());
    assertTrue(ProcedureTestingUtility.getExceptionCause(result) instanceof ConstraintException);
  }

};