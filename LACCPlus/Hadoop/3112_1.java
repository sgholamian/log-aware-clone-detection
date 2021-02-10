//,temp,FSDirRenameOp.java,464,496,temp,FSDirRenameOp.java,250,304
//,3
public class xxx {
  @Deprecated
  private static RenameResult renameTo(FSDirectory fsd, FSPermissionChecker pc,
      INodesInPath srcIIP, INodesInPath dstIIP, boolean logRetryCache)
          throws IOException {
    if (fsd.isPermissionEnabled()) {
      // Check write access to parent of src
      fsd.checkPermission(pc, srcIIP, false, null, FsAction.WRITE, null, null,
          false);
      // Check write access to ancestor of dst
      fsd.checkPermission(pc, dstIIP, false, FsAction.WRITE, null, null,
          null, false);
    }

    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* FSDirectory.renameTo: " +
          srcIIP.getPath() + " to " + dstIIP.getPath());
    }
    final long mtime = Time.now();
    INodesInPath renameIIP;
    fsd.writeLock();
    try {
      renameIIP = unprotectedRenameTo(fsd, srcIIP, dstIIP, mtime);
    } finally {
      fsd.writeUnlock();
    }
    if (renameIIP != null) {
      fsd.getEditLog().logRename(
          srcIIP.getPath(), dstIIP.getPath(), mtime, logRetryCache);
    }
    // this rename never overwrites the dest so files deleted and collected
    // are irrelevant.
    return createRenameResult(fsd, renameIIP, false, null);
  }

};