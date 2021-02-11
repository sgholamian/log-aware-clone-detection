//,temp,FSDirRenameOp.java,464,496,temp,FSDirRenameOp.java,250,304
//,3
public class xxx {
  static RenameResult renameTo(FSDirectory fsd, FSPermissionChecker pc,
      String src, String dst, BlocksMapUpdateInfo collectedBlocks,
      boolean logRetryCache,Options.Rename... options)
          throws IOException {
    final INodesInPath srcIIP = fsd.resolvePath(pc, src, DirOp.WRITE_LINK);
    final INodesInPath dstIIP = fsd.resolvePath(pc, dst, DirOp.CREATE_LINK);
    if (fsd.isPermissionEnabled()) {
      boolean renameToTrash = false;
      if (null != options &&
          Arrays.asList(options).
          contains(Options.Rename.TO_TRASH)) {
        renameToTrash = true;
      }

      if(renameToTrash) {
        // if destination is the trash directory,
        // besides the permission check on "rename"
        // we need to enforce the check for "delete"
        // otherwise, it would expose a
        // security hole that stuff moved to trash
        // will be deleted by superuser
        fsd.checkPermission(pc, srcIIP, false, null, FsAction.WRITE, null,
            FsAction.ALL, true);
      } else {
        // Rename does not operate on link targets
        // Do not resolveLink when checking permissions of src and dst
        // Check write access to parent of src
        fsd.checkPermission(pc, srcIIP, false, null, FsAction.WRITE, null,
            null, false);
      }
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
    final RenameResult result;
    try {
      result = unprotectedRenameTo(fsd, srcIIP, dstIIP, mtime,
          collectedBlocks, options);
      if (result.filesDeleted) {
        FSDirDeleteOp.incrDeletedFileCount(1);
      }
    } finally {
      fsd.writeUnlock();
    }
    fsd.getEditLog().logRename(
        srcIIP.getPath(), dstIIP.getPath(), mtime, logRetryCache, options);
    return result;
  }

};