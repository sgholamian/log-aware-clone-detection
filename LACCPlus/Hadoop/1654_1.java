//,temp,DFSClientCache.java,260,270,temp,DFSClientCache.java,249,258
//,3
public class xxx {
  FSDataInputStream getDfsInputStream(String userName, String inodePath) {
    DFSInputStreamCaheKey k = new DFSInputStreamCaheKey(userName, inodePath);
    FSDataInputStream s = null;
    try {
      s = inputstreamCache.get(k);
    } catch (ExecutionException e) {
      LOG.warn("Failed to create DFSInputStream for user:" + userName
          + " Cause:" + e);
    }
    return s;
  }

};