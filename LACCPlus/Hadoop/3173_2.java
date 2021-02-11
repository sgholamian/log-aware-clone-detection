//,temp,NameNodeRpcServer.java,786,813,temp,NameNodeRpcServer.java,748,784
//,3
public class xxx {
  @Override // ClientProtocol
  public HdfsFileStatus create(String src, FsPermission masked,
      String clientName, EnumSetWritable<CreateFlag> flag,
      boolean createParent, short replication, long blockSize,
      CryptoProtocolVersion[] supportedVersions, String ecPolicyName)
      throws IOException {
    checkNNStartup();
    String clientMachine = getClientMachine();
    if (stateChangeLog.isDebugEnabled()) {
      stateChangeLog.debug("*DIR* NameNode.create: file "
          +src+" for "+clientName+" at "+clientMachine);
    }
    if (!checkPathLength(src)) {
      throw new IOException("create: Pathname too long.  Limit "
          + MAX_PATH_LENGTH + " characters, " + MAX_PATH_DEPTH + " levels.");
    }
    namesystem.checkOperation(OperationCategory.WRITE);
    CacheEntryWithPayload cacheEntry = RetryCache.waitForCompletion(retryCache, null);
    if (cacheEntry != null && cacheEntry.isSuccess()) {
      return (HdfsFileStatus) cacheEntry.getPayload();
    }

    HdfsFileStatus status = null;
    try {
      PermissionStatus perm = new PermissionStatus(getRemoteUser()
          .getShortUserName(), null, masked);
      status = namesystem.startFile(src, perm, clientName, clientMachine,
          flag.get(), createParent, replication, blockSize, supportedVersions,
          ecPolicyName, cacheEntry != null);
    } finally {
      RetryCache.setState(cacheEntry, status != null, status);
    }

    metrics.incrFilesCreated();
    metrics.incrCreateFileOps();
    return status;
  }

};