//,temp,FSNamesystem.java,4826,4842,temp,FSNamesystem.java,4810,4824
//,3
public class xxx {
  CheckpointSignature rollEditLog() throws IOException {
    checkSuperuserPrivilege();
    checkOperation(OperationCategory.JOURNAL);
    writeLock();
    try {
      checkOperation(OperationCategory.JOURNAL);
      checkNameNodeSafeMode("Log not rolled");
      if (Server.isRpcInvocation()) {
        LOG.info("Roll Edit Log from " + Server.getRemoteAddress());
      }
      return getFSImage().rollEditLog(getEffectiveLayoutVersion());
    } finally {
      writeUnlock();
    }
  }

};