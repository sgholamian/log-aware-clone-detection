//,temp,FSDirRenameOp.java,227,253,temp,FSDirRenameOp.java,51,79
//,3
public class xxx {
  static Map.Entry<BlocksMapUpdateInfo, HdfsFileStatus> renameToInt(
      FSDirectory fsd, final String srcArg, final String dstArg,
      boolean logRetryCache, Options.Rename... options)
      throws IOException {
    String src = srcArg;
    String dst = dstArg;
    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("DIR* NameSystem.renameTo: with options -" +
          " " + src + " to " + dst);
    }
    if (!DFSUtil.isValidName(dst)) {
      throw new InvalidPathException("Invalid name: " + dst);
    }
    final FSPermissionChecker pc = fsd.getPermissionChecker();

    byte[][] srcComponents = FSDirectory.getPathComponentsForReservedPath(src);
    byte[][] dstComponents = FSDirectory.getPathComponentsForReservedPath(dst);
    BlocksMapUpdateInfo collectedBlocks = new BlocksMapUpdateInfo();
    src = fsd.resolvePath(pc, src, srcComponents);
    dst = fsd.resolvePath(pc, dst, dstComponents);
    renameTo(fsd, pc, src, dst, collectedBlocks, logRetryCache, options);
    INodesInPath dstIIP = fsd.getINodesInPath(dst, false);
    HdfsFileStatus resultingStat = fsd.getAuditFileInfo(dstIIP);

    return new AbstractMap.SimpleImmutableEntry<>(
        collectedBlocks, resultingStat);
  }

};