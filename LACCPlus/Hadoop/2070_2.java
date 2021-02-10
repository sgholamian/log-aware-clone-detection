//,temp,FSNamesystem.java,2331,2376,temp,FSNamesystem.java,1889,1925
//,3
public class xxx {
  boolean truncate(String src, long newLength, String clientName,
      String clientMachine, long mtime) throws IOException,
      UnresolvedLinkException {

    requireEffectiveLayoutVersionForFeature(Feature.TRUNCATE);
    final FSDirTruncateOp.TruncateResult r;
    try {
      NameNode.stateChangeLog.debug(
          "DIR* NameSystem.truncate: src={} newLength={}", src, newLength);
      if (newLength < 0) {
        throw new HadoopIllegalArgumentException(
            "Cannot truncate to a negative file size: " + newLength + ".");
      }
      final FSPermissionChecker pc = getPermissionChecker();
      checkOperation(OperationCategory.WRITE);
      writeLock();
      BlocksMapUpdateInfo toRemoveBlocks = new BlocksMapUpdateInfo();
      try {
        checkOperation(OperationCategory.WRITE);
        checkNameNodeSafeMode("Cannot truncate for " + src);
        r = FSDirTruncateOp.truncate(this, src, newLength, clientName,
            clientMachine, mtime, toRemoveBlocks, pc);
      } finally {
        writeUnlock();
      }
      getEditLog().logSync();
      if (!toRemoveBlocks.getToDeleteList().isEmpty()) {
        removeBlocks(toRemoveBlocks);
        toRemoveBlocks.clear();
      }
      logAuditEvent(true, "truncate", src, null, r.getFileStatus());
    } catch (AccessControlException e) {
      logAuditEvent(false, "truncate", src);
      throw e;
    }
    return r.getResult();
  }

};