//,temp,FSNamesystem.java,7539,7560,temp,FSNamesystem.java,4671,4687
//,3
public class xxx {
  void enableErasureCodingPolicy(String ecPolicyName,
      final boolean logRetryCache) throws IOException {
    final String operationName = "enableErasureCodingPolicy";
    checkOperation(OperationCategory.WRITE);
    boolean success = false;
    LOG.info("Enable the erasure coding policy " + ecPolicyName);
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Cannot enable erasure coding policy "
          + ecPolicyName);
      FSDirErasureCodingOp.enableErasureCodingPolicy(this, ecPolicyName,
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