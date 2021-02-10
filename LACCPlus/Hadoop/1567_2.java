//,temp,FSDirRenameOp.java,445,482,temp,FSDirRenameOp.java,259,290
//,3
public class xxx {
  static void renameTo(FSDirectory fsd, FSPermissionChecker pc, String src,
      String dst, BlocksMapUpdateInfo collectedBlocks, boolean logRetryCache,
      Options.Rename... options) throws IOException {
    final INodesInPath srcIIP = fsd.getINodesInPath4Write(src, false);
    final INodesInPath dstIIP = fsd.getINodesInPath4Write(dst, false);
    if (fsd.isPermissionEnabled()) {
      // Rename does not operate on link targets
      // Do not resolveLink when checking permissions of src and dst
      // Check write access to parent of src
      fsd.checkPermission(pc, srcIIP, false, null, FsAction.WRITE, null, null,
          false);
      // Check write access to ancestor of dst
      fsd.checkPermission(pc, dstIIP, false, FsAction.WRITE, null, null, null,
          false);
    }

    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* FSDirectory.renameTo: " + src + " to "
          + dst);
    }
    final long mtime = Time.now();
    fsd.writeLock();
    try {
      if (unprotectedRenameTo(fsd, src, dst, srcIIP, dstIIP, mtime,
          collectedBlocks, options)) {
        FSDirDeleteOp.incrDeletedFileCount(1);
      }
    } finally {
      fsd.writeUnlock();
    }
    fsd.getEditLog().logRename(src, dst, mtime, logRetryCache, options);
  }

};