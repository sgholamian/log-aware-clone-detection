//,temp,NameNodeRpcServer.java,882,906,temp,NameNodeRpcServer.java,835,862
//,3
public class xxx {
  @Deprecated
  @Override // ClientProtocol
  public boolean rename(String src, String dst) throws IOException {
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
      return true; // Return previous response
    }

    boolean ret = false;
    try {
      ret = namesystem.renameTo(src, dst, cacheEntry != null);
    } finally {
      RetryCache.setState(cacheEntry, ret);
    }
    if (ret) {
      metrics.incrFilesRenamed();
    }
    return ret;
  }

};