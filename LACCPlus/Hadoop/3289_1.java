//,temp,FSDirSymlinkOp.java,36,82,temp,FSDirMkdirOp.java,42,88
//,3
public class xxx {
  static FileStatus createSymlinkInt(
      FSNamesystem fsn, String target, final String linkArg,
      PermissionStatus dirPerms, boolean createParent, boolean logRetryCache)
      throws IOException {
    FSDirectory fsd = fsn.getFSDirectory();
    String link = linkArg;
    if (!DFSUtil.isValidName(link)) {
      throw new InvalidPathException("Invalid link name: " + link);
    }
    if (FSDirectory.isReservedName(target) || target.isEmpty()
        || FSDirectory.isExactReservedName(target)) {
      throw new InvalidPathException("Invalid target name: " + target);
    }

    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* NameSystem.createSymlink: target="
          + target + " link=" + link);
    }

    FSPermissionChecker pc = fsn.getPermissionChecker();
    INodesInPath iip;
    fsd.writeLock();
    try {
      iip = fsd.resolvePath(pc, link, DirOp.WRITE_LINK);
      link = iip.getPath();
      if (!createParent) {
        fsd.verifyParentDir(iip);
      }
      if (!fsd.isValidToCreate(link, iip)) {
        throw new IOException(
            "failed to create link " + link +
                " either because the filename is invalid or the file exists");
      }
      if (fsd.isPermissionEnabled()) {
        fsd.checkAncestorAccess(pc, iip, FsAction.WRITE);
      }
      // validate that we have enough inodes.
      fsn.checkFsObjectLimit();

      // add symbolic link to namespace
      addSymlink(fsd, link, iip, target, dirPerms, createParent, logRetryCache);
    } finally {
      fsd.writeUnlock();
    }
    NameNode.getNameNodeMetrics().incrCreateSymlinkOps();
    return fsd.getAuditFileInfo(iip);
  }

};