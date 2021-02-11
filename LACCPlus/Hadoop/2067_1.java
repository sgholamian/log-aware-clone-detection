//,temp,NameNodeRpcServer.java,882,906,temp,NameNodeRpcServer.java,638,665
//,3
public class xxx {
  @Override // ClientProtocol
  public void rename2(String src, String dst, Options.Rename... options)
      throws IOException {
    checkNNStartup();
    if(stateChangeLog.isDebugEnabled()) {
      stateChangeLog.debug("*DIR* NameNode.rename: " + src + " to " + dst);
    }
    if (!checkPathLength(dst)) {
      throw new IOException("rename: Pathname too long.  Limit "
          + MAX_PATH_LENGTH + " characters, " + MAX_PATH_DEPTH + " levels.");
    }
    namesystem.checkOperation(OperationCategory.WRITE);
    CacheEntry cacheEntry = RetryCache.waitForCompletion(retryCache);
    if (cacheEntry != null && cacheEntry.isSuccess()) {
      return; // Return previous response
    }
    boolean success = false;
    try {
      namesystem.renameTo(src, dst, cacheEntry != null, options);
      success = true;
    } finally {
      RetryCache.setState(cacheEntry, success);
    }
    metrics.incrFilesRenamed();
  }

};