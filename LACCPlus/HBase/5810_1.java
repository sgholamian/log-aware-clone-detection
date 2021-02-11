//,temp,TestModifyNamespaceProcedure.java,127,153,temp,TestCreateNamespaceProcedure.java,127,142
//,3
public class xxx {
  @Test
  public void testModifyNonExistNamespace() throws Exception {
    final String namespaceName = "testModifyNonExistNamespace";
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    try {
      NamespaceDescriptor nsDescriptor = UTIL.getAdmin().getNamespaceDescriptor(namespaceName);
      assertNull(nsDescriptor);
    } catch (NamespaceNotFoundException nsnfe) {
      // Expected
      LOG.debug("The namespace " + namespaceName + " does not exist.  This is expected.");
    }

    final NamespaceDescriptor nsd = NamespaceDescriptor.create(namespaceName).build();

    long procId = procExec.submitProcedure(
      new ModifyNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);

    // Expect fail with NamespaceNotFoundException
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("modify namespace failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof NamespaceNotFoundException);
  }

};