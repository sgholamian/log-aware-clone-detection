//,temp,CuratorService.java,690,703,temp,CuratorService.java,465,481
//,3
public class xxx {
  public List<ACL> zkGetACLS(String path) throws IOException {
    checkServiceLive();
    String fullpath = createFullPath(path);
    List<ACL> acls;
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("GetACLS {}", fullpath);
      }
      acls = curator.getACL().forPath(fullpath);
    } catch (Exception e) {
      throw operationFailure(fullpath, "read()", e);
    }
    if (acls == null) {
      throw new PathNotFoundException(path);
    }
    return acls;
  }

};