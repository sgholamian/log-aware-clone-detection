//,temp,FSDirRenameOp.java,445,482,temp,FSDirRenameOp.java,259,290
//,3
public class xxx {
  @Deprecated
  @SuppressWarnings("deprecation")
  private static boolean renameTo(FSDirectory fsd, FSPermissionChecker pc,
      String src, String dst, boolean logRetryCache) throws IOException {
    // Rename does not operate on link targets
    // Do not resolveLink when checking permissions of src and dst
    // Check write access to parent of src
    final INodesInPath srcIIP = fsd.getINodesInPath4Write(src, false);
    // Note: We should not be doing this.  This is move() not renameTo().
    final String actualDst = fsd.isDir(dst) ?
        dst + Path.SEPARATOR + new Path(src).getName() : dst;
    final INodesInPath dstIIP = fsd.getINodesInPath4Write(actualDst, false);
    if (fsd.isPermissionEnabled()) {
      fsd.checkPermission(pc, srcIIP, false, null, FsAction.WRITE, null, null,
          false);
      // Check write access to ancestor of dst
      fsd.checkPermission(pc, dstIIP, false, FsAction.WRITE, null, null,
          null, false);
    }

    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* FSDirectory.renameTo: " + src + " to "
          + dst);
    }
    final long mtime = Time.now();
    boolean stat = false;
    fsd.writeLock();
    try {
      stat = unprotectedRenameTo(fsd, src, actualDst, srcIIP, dstIIP, mtime);
    } finally {
      fsd.writeUnlock();
    }
    if (stat) {
      fsd.getEditLog().logRename(src, actualDst, mtime, logRetryCache);
      return true;
    }
    return false;
  }

};