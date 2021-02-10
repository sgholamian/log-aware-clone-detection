//,temp,CuratorService.java,608,620,temp,CuratorService.java,583,600
//,3
public class xxx {
  public void zkUpdate(String path, byte[] data) throws IOException {
    Preconditions.checkArgument(data != null, "null data");
    checkServiceLive();
    path = createFullPath(path);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Updating {} with {} bytes", path, data.length);
      }
      curator.setData().forPath(path, data);
    } catch (Exception e) {
      throw operationFailure(path, "update()", e);
    }
  }

};