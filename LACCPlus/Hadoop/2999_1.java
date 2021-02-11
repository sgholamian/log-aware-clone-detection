//,temp,CuratorService.java,758,769,temp,CuratorService.java,651,663
//,3
public class xxx {
  public byte[] zkRead(String path) throws IOException {
    checkServiceLive();
    String fullpath = createFullPath(path);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Reading {}", fullpath);
      }
      return curator.getData().forPath(fullpath);
    } catch (Exception e) {
      throw operationFailure(fullpath, "read()", e);
    }
  }

};