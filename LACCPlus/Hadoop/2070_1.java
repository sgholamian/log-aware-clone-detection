//,temp,FSNamesystem.java,2331,2376,temp,FSNamesystem.java,1889,1925
//,3
public class xxx {
  LastBlockWithStatus appendFile(String srcArg, String holder,
      String clientMachine, EnumSet<CreateFlag> flag, boolean logRetryCache)
      throws IOException {
    boolean newBlock = flag.contains(CreateFlag.NEW_BLOCK);
    if (newBlock) {
      requireEffectiveLayoutVersionForFeature(Feature.APPEND_NEW_BLOCK);
    }

    if (!supportAppends) {
      throw new UnsupportedOperationException(
          "Append is not enabled on this NameNode. Use the " +
          DFS_SUPPORT_APPEND_KEY + " configuration option to enable it.");
    }

    NameNode.stateChangeLog.debug(
        "DIR* NameSystem.appendFile: src={}, holder={}, clientMachine={}",
        srcArg, holder, clientMachine);
    try {
      boolean skipSync = false;
      LastBlockWithStatus lbs = null;
      final FSPermissionChecker pc = getPermissionChecker();
      checkOperation(OperationCategory.WRITE);
      writeLock();
      try {
        checkOperation(OperationCategory.WRITE);
        checkNameNodeSafeMode("Cannot append to file" + srcArg);
        lbs = FSDirAppendOp.appendFile(this, srcArg, pc, holder, clientMachine,
            newBlock, logRetryCache);
      } catch (StandbyException se) {
        skipSync = true;
        throw se;
      } finally {
        writeUnlock();
        // There might be transactions logged while trying to recover the lease
        // They need to be sync'ed even when an exception was thrown.
        if (!skipSync) {
          getEditLog().logSync();
        }
      }
      logAuditEvent(true, "append", srcArg);
      return lbs;
    } catch (AccessControlException e) {
      logAuditEvent(false, "append", srcArg);
      throw e;
    }
  }

};