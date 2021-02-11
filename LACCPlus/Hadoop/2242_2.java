//,temp,CuratorService.java,625,642,temp,CuratorService.java,564,598
//,3
public class xxx {
  public boolean zkMkPath(String path,
      CreateMode mode,
      boolean createParents,
      List<ACL> acls)
      throws IOException {
    checkServiceLive();
    path = createFullPath(path);
    if (acls == null || acls.isEmpty()) {
      throw new NoPathPermissionsException(path, "Empty ACL list");
    }

    try {
      RegistrySecurity.AclListInfo aclInfo =
          new RegistrySecurity.AclListInfo(acls);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Creating path {} with mode {} and ACL {}",
            path, mode, aclInfo);
      }
      CreateBuilder createBuilder = curator.create();
      createBuilder.withMode(mode).withACL(acls);
      if (createParents) {
        createBuilder.creatingParentsIfNeeded();
      }
      createBuilder.forPath(path);

    } catch (KeeperException.NodeExistsException e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("path already present: {}", path, e);
      }
      return false;
    } catch (Exception e) {
      throw operationFailure(path, "mkdir() ", e, acls);
    }
    return true;
  }

};