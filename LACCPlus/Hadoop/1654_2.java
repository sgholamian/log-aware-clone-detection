//,temp,DFSClientCache.java,260,270,temp,DFSClientCache.java,249,258
//,3
public class xxx {
  DFSClient getDfsClient(String userName) {
    DFSClient client = null;
    try {
      client = clientCache.get(userName);
    } catch (ExecutionException e) {
      LOG.error("Failed to create DFSClient for user:" + userName + " Cause:"
          + e);
    }
    return client;
  }

};