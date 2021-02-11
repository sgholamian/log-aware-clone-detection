//,temp,DefaultContainerExecutor.java,721,745,temp,DefaultContainerExecutor.java,696,716
//,3
public class xxx {
  void createAppLogDirs(String appId, List<String> logDirs, String user)
      throws IOException {

    boolean appLogDirStatus = false;
    FsPermission appLogDirPerms = new FsPermission(LOGDIR_PERM);
    for (String rootLogDir : logDirs) {
      // create $log.dir/$appid
      Path appLogDir = new Path(rootLogDir, appId);
      try {
        createDir(appLogDir, appLogDirPerms, true, user);
      } catch (IOException e) {
        LOG.warn("Unable to create the app-log directory : " + appLogDir, e);
        continue;
      }
      appLogDirStatus = true;
    }
    if (!appLogDirStatus) {
      throw new IOException("Not able to initialize app-log directories "
          + "in any of the configured local directories for app " + appId);
    }
  }

};