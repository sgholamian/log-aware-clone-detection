//,temp,FSNamesystem.java,7539,7560,temp,FSNamesystem.java,3276,3297
//,3
public class xxx {
  void fsync(String src, long fileId, String clientName, long lastBlockLength)
      throws IOException {
    NameNode.stateChangeLog.info("BLOCK* fsync: " + src + " for " + clientName);
    checkOperation(OperationCategory.WRITE);
    final FSPermissionChecker pc = getPermissionChecker();
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Cannot fsync file " + src);
      INodesInPath iip = dir.resolvePath(pc, src, fileId);
      src = iip.getPath();
      final INodeFile pendingFile = checkLease(iip, clientName, fileId);
      if (lastBlockLength > 0) {
        pendingFile.getFileUnderConstructionFeature().updateLengthOfLastBlock(
            pendingFile, lastBlockLength);
      }
      FSDirWriteFileOp.persistBlocks(dir, src, pendingFile, false);
    } finally {
      writeUnlock("fsync");
    }
    getEditLog().logSync();
  }

};