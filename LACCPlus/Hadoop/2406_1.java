//,temp,DefaultContainerExecutor.java,950,974,temp,DefaultContainerExecutor.java,916,937
//,3
public class xxx {
  void createContainerLogDirs(String appId, String containerId,
      List<String> logDirs, String user) throws IOException {
    boolean containerLogDirStatus = false;
    FsPermission containerLogDirPerms = new
        FsPermission(getLogDirPermissions());
    for (String rootLogDir : logDirs) {
      // create $log.dir/$appid/$containerid
      Path appLogDir = new Path(rootLogDir, appId);
      Path containerLogDir = new Path(appLogDir, containerId);
      try {
        createDir(containerLogDir, containerLogDirPerms, true, user);
      } catch (IOException e) {
        LOG.warn("Unable to create the container-log directory : "
            + appLogDir, e);
        continue;
      }
      containerLogDirStatus = true;
    }
    if (!containerLogDirStatus) {
      throw new IOException(
          "Not able to initialize container-log directories "
              + "in any of the configured local directories for container "
              + containerId);
    }
  }

};