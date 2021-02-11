//,temp,TestModifyNamespaceProcedure.java,127,153,temp,TestCreateNamespaceProcedure.java,127,142
//,3
public class xxx {
  @Test
  public void testCreateSystemNamespace() throws Exception {
    final NamespaceDescriptor nsd =
        UTIL.getAdmin().getNamespaceDescriptor(NamespaceDescriptor.SYSTEM_NAMESPACE.getName());
    final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();

    long procId = procExec.submitProcedure(
      new CreateNamespaceProcedure(procExec.getEnvironment(), nsd));
    // Wait the completion
    ProcedureTestingUtility.waitProcedure(procExec, procId);
    Procedure<?> result = procExec.getResult(procId);
    assertTrue(result.isFailed());
    LOG.debug("Create namespace failed with exception: " + result.getException());
    assertTrue(
      ProcedureTestingUtility.getExceptionCause(result) instanceof NamespaceExistException);
  }

};