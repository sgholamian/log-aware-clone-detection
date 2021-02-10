//,temp,FSNamesystem.java,3276,3297,temp,FSNamesystem.java,2618,2658
//,3
public class xxx {
  LastBlockWithStatus appendFile(String srcArg, String holder,
      String clientMachine, EnumSet<CreateFlag> flag, boolean logRetryCache)
      throws IOException {
    final String operationName = "append";
    boolean newBlock = flag.contains(CreateFlag.NEW_BLOCK);
    if (newBlock) {
      requireEffectiveLayoutVersionForFeature(Feature.APPEND_NEW_BLOCK);
    }

    NameNode.stateChangeLog.debug(
        "DIR* NameSystem.appendFile: src={}, holder={}, clientMachine={}",
        srcArg, holder, clientMachine);
    try {
      boolean skipSync = false;
      LastBlockWithStatus lbs = null;
      checkOperation(OperationCategory.WRITE);
      final FSPermissionChecker pc = getPermissionChecker();
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
        writeUnlock(operationName);
        // There might be transactions logged while trying to recover the lease
        // They need to be sync'ed even when an exception was thrown.
        if (!skipSync) {
          getEditLog().logSync();
        }
      }
      logAuditEvent(true, operationName, srcArg);
      return lbs;
    } catch (AccessControlException e) {
      logAuditEvent(false, operationName, srcArg);
      throw e;
    }
  }

};