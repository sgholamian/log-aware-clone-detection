//,temp,FSDirRenameOp.java,227,253,temp,FSDirRenameOp.java,51,79
//,3
public class xxx {
  @Deprecated
  static RenameOldResult renameToInt(
      FSDirectory fsd, final String srcArg, final String dstArg,
      boolean logRetryCache)
      throws IOException {
    String src = srcArg;
    String dst = dstArg;
    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* NameSystem.renameTo: " + src +
          " to " + dst);
    }
    if (!DFSUtil.isValidName(dst)) {
      throw new IOException("Invalid name: " + dst);
    }
    FSPermissionChecker pc = fsd.getPermissionChecker();

    byte[][] srcComponents = FSDirectory.getPathComponentsForReservedPath(src);
    byte[][] dstComponents = FSDirectory.getPathComponentsForReservedPath(dst);
    HdfsFileStatus resultingStat = null;
    src = fsd.resolvePath(pc, src, srcComponents);
    dst = fsd.resolvePath(pc, dst, dstComponents);
    @SuppressWarnings("deprecation")
    final boolean status = renameTo(fsd, pc, src, dst, logRetryCache);
    if (status) {
      INodesInPath dstIIP = fsd.getINodesInPath(dst, false);
      resultingStat = fsd.getAuditFileInfo(dstIIP);
    }
    return new RenameOldResult(status, resultingStat);
  }

};