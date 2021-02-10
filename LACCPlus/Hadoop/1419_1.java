//,temp,NameNodeRpcServer.java,924,946,temp,NameNodeRpcServer.java,882,906
//,3
public class xxx {
  @Override // ClientProtocol
  public boolean delete(String src, boolean recursive) throws IOException {
    checkNNStartup();
    if (stateChangeLog.isDebugEnabled()) {
      stateChangeLog.debug("*DIR* Namenode.delete: src=" + src
          + ", recursive=" + recursive);
    }
    namesystem.checkOperation(OperationCategory.WRITE);
    CacheEntry cacheEntry = RetryCache.waitForCompletion(retryCache);
    if (cacheEntry != null && cacheEntry.isSuccess()) {
      return true; // Return previous response
    }

    boolean ret = false;
    try {
      ret = namesystem.delete(src, recursive, cacheEntry != null);
    } finally {
      RetryCache.setState(cacheEntry, ret);
    }
    if (ret) 
      metrics.incrDeleteFileOps();
    return ret;
  }

};