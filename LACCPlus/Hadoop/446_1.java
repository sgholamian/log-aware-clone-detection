//,temp,NameNodeRpcServer.java,638,665,temp,NameNodeRpcServer.java,600,636
//,3
public class xxx {
  @Override // ClientProtocol
  public LastBlockWithStatus append(String src, String clientName,
      EnumSetWritable<CreateFlag> flag) throws IOException {
    checkNNStartup();
    String clientMachine = getClientMachine();
    if (stateChangeLog.isDebugEnabled()) {
      stateChangeLog.debug("*DIR* NameNode.append: file "
          +src+" for "+clientName+" at "+clientMachine);
    }
    namesystem.checkOperation(OperationCategory.WRITE);
    CacheEntryWithPayload cacheEntry = RetryCache.waitForCompletion(retryCache,
        null);
    if (cacheEntry != null && cacheEntry.isSuccess()) {
      return (LastBlockWithStatus) cacheEntry.getPayload();
    }

    LastBlockWithStatus info = null;
    boolean success = false;
    try {
      info = namesystem.appendFile(src, clientName, clientMachine, flag.get(),
          cacheEntry != null);
      success = true;
    } finally {
      RetryCache.setState(cacheEntry, success, info);
    }
    metrics.incrFilesAppended();
    return info;
  }

};