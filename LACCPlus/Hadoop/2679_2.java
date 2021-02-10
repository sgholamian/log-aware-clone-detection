//,temp,NamenodeBeanMetrics.java,354,362,temp,DFSClientCache.java,324,332
//,3
public class xxx {
  DFSClient getDfsClient(String userName, int namenodeId) {
    DFSClient client = null;
    try {
      client = clientCache.get(new DfsClientKey(userName, namenodeId));
    } catch (ExecutionException e) {
      LOG.error("Failed to create DFSClient for user: {}", userName, e);
    }
    return client;
  }

};