//,temp,FSNamesystem.java,7569,7590,temp,FSNamesystem.java,2848,2869
//,3
public class xxx {
  void disableErasureCodingPolicy(String ecPolicyName,
      final boolean logRetryCache) throws IOException {
    final String operationName = "disableErasureCodingPolicy";
    checkOperation(OperationCategory.WRITE);
    boolean success = false;
    LOG.info("Disable the erasure coding policy " + ecPolicyName);
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Cannot disable erasure coding policy "
          + ecPolicyName);
      FSDirErasureCodingOp.disableErasureCodingPolicy(this, ecPolicyName,
          logRetryCache);
      success = true;
    } finally {
      writeUnlock(operationName);
      if (success) {
        getEditLog().logSync();
      }
      logAuditEvent(success, operationName, ecPolicyName, null, null);
    }
  }

};