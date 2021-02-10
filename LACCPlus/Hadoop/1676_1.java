//,temp,CuratorService.java,711,722,temp,CuratorService.java,608,620
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