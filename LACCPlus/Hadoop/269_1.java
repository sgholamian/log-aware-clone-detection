//,temp,CuratorService.java,690,703,temp,CuratorService.java,465,481
//,3
public class xxx {
  public List<String> zkList(String path) throws IOException {
    checkServiceLive();
    String fullpath = createFullPath(path);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("ls {}", fullpath);
      }
      GetChildrenBuilder builder = curator.getChildren();
      List<String> children = builder.forPath(fullpath);
      return children;
    } catch (Exception e) {
      throw operationFailure(path, "ls()", e);
    }
  }

};