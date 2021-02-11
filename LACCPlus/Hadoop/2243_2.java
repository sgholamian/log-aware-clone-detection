//,temp,CuratorService.java,736,749,temp,CuratorService.java,705,727
//,3
public class xxx {
  public void zkDelete(String path,
      boolean recursive,
      BackgroundCallback backgroundCallback) throws IOException {
    checkServiceLive();
    String fullpath = createFullPath(path);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Deleting {}", fullpath);
      }
      DeleteBuilder delete = curator.delete();
      if (recursive) {
        delete.deletingChildrenIfNeeded();
      }
      if (backgroundCallback != null) {
        delete.inBackground(backgroundCallback);
      }
      delete.forPath(fullpath);
    } catch (KeeperException.NoNodeException e) {
      // not an error
    } catch (Exception e) {
      throw operationFailure(fullpath, "delete()", e);
    }
  }

};