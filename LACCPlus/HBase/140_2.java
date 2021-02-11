//,temp,TestModifyNamespaceProcedure.java,155,176,temp,TestDeleteNamespaceProcedure.java,113,130
//,3
public class xxx {
  @Test
  public void testDeleteNonExistNamespace() throws Exception {
    final String namespaceName = "testDeleteNonExistNamespace";
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    validateNamespaceNotExist(namespaceName);

    long procId = procExec.submitProcedure(
      new DeleteNamespaceProcedure(procExec.getEnvironment(), namespaceName));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);
    // Expect fail with NamespaceNotFoundException
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Delete namespace failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof NamespaceNotFoundException);
  }

};