//,temp,FSNamesystem.java,4826,4842,temp,FSNamesystem.java,2507,2525
//,3
public class xxx {
  void abandonBlock(ExtendedBlock b, long fileId, String src, String holder)
      throws IOException {
    NameNode.stateChangeLog.debug(
        "BLOCK* NameSystem.abandonBlock: {} of file {}", b, src);
    waitForLoadingFSImage();
    checkOperation(OperationCategory.WRITE);
    FSPermissionChecker pc = getPermissionChecker();
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Cannot abandon block " + b + " for file" + src);
      FSDirWriteFileOp.abandonBlock(dir, pc, b, fileId, src, holder);
      NameNode.stateChangeLog.debug("BLOCK* NameSystem.abandonBlock: {} is" +
          " removed from pendingCreates", b);
    } finally {
      writeUnlock();
    }
    getEditLog().logSync();
  }

};