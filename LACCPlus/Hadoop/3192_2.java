//,temp,FSNamesystem.java,3276,3297,temp,FSNamesystem.java,2848,2869
//,3
public class xxx {
  boolean completeFile(final String src, String holder,
                       ExtendedBlock last, long fileId)
    throws IOException {
    boolean success = false;
    checkOperation(OperationCategory.WRITE);
    final FSPermissionChecker pc = getPermissionChecker();
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Cannot complete file " + src);
      success = FSDirWriteFileOp.completeFile(this, pc, src, holder, last,
                                              fileId);
    } finally {
      writeUnlock("completeFile");
    }
    getEditLog().logSync();
    if (success) {
      NameNode.stateChangeLog.info("DIR* completeFile: " + src
          + " is closed by " + holder);
    }
    return success;
  }

};