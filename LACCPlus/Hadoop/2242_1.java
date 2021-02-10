//,temp,CuratorService.java,625,642,temp,CuratorService.java,564,598
//,3
public class xxx {
  public void zkCreate(String path,
      CreateMode mode,
      byte[] data,
      List<ACL> acls) throws IOException {
    Preconditions.checkArgument(data != null, "null data");
    checkServiceLive();
    String fullpath = createFullPath(path);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Creating {} with {} bytes of data and ACL {}",
            fullpath, data.length,
            new RegistrySecurity.AclListInfo(acls));
      }
      curator.create().withMode(mode).withACL(acls).forPath(fullpath, data);
    } catch (Exception e) {
      throw operationFailure(fullpath, "create()", e, acls);
    }
  }

};