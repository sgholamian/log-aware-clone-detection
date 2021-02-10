//,temp,CuratorService.java,651,663,temp,CuratorService.java,477,493
//,3
public class xxx {
  public Stat zkStat(String path) throws IOException {
    checkServiceLive();
    String fullpath = createFullPath(path);
    Stat stat;
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Stat {}", fullpath);
      }
      stat = curator.checkExists().forPath(fullpath);
    } catch (Exception e) {
      throw operationFailure(fullpath, "read()", e);
    }
    if (stat == null) {
      throw new PathNotFoundException(path);
    }
    return stat;
  }

};