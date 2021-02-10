//,temp,FSDirSymlinkOp.java,36,82,temp,FSDirMkdirOp.java,42,88
//,3
public class xxx {
  static FileStatus mkdirs(FSNamesystem fsn, FSPermissionChecker pc, String src,
      PermissionStatus permissions, boolean createParent) throws IOException {
    FSDirectory fsd = fsn.getFSDirectory();
    if(NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* NameSystem.mkdirs: " + src);
    }
    fsd.writeLock();
    try {
      INodesInPath iip = fsd.resolvePath(pc, src, DirOp.CREATE);

      final INode lastINode = iip.getLastINode();
      if (lastINode != null && lastINode.isFile()) {
        throw new FileAlreadyExistsException("Path is not a directory: " + src);
      }

      if (lastINode == null) {
        if (fsd.isPermissionEnabled()) {
          fsd.checkAncestorAccess(pc, iip, FsAction.WRITE);
        }

        if (!createParent) {
          fsd.verifyParentDir(iip);
        }

        // validate that we have enough inodes. This is, at best, a
        // heuristic because the mkdirs() operation might need to
        // create multiple inodes.
        fsn.checkFsObjectLimit();

        // Ensure that the user can traversal the path by adding implicit
        // u+wx permission to all ancestor directories.
        INodesInPath existing =
            createParentDirectories(fsd, iip, permissions, false);
        if (existing != null) {
          existing = createSingleDirectory(
              fsd, existing, iip.getLastLocalName(), permissions);
        }
        if (existing == null) {
          throw new IOException("Failed to create directory: " + src);
        }
        iip = existing;
      }
      return fsd.getAuditFileInfo(iip);
    } finally {
      fsd.writeUnlock();
    }
  }

};