//,temp,DefaultContainerExecutor.java,721,745,temp,DockerContainerExecutor.java,703,727
//,2
public class xxx {
  void createContainerLogDirs(String appId, String containerId,
      List<String> logDirs, String user) throws IOException {

    boolean containerLogDirStatus = false;
    FsPermission containerLogDirPerms = new FsPermission(LOGDIR_PERM);
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