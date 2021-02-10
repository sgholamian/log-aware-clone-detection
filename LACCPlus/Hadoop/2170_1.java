//,temp,DefaultContainerExecutor.java,625,663,temp,DockerContainerExecutor.java,631,669
//,2
public class xxx {
  void createUserCacheDirs(List<String> localDirs, String user)
      throws IOException {
    LOG.info("Initializing user " + user);

    boolean appcacheDirStatus = false;
    boolean distributedCacheDirStatus = false;
    FsPermission appCachePerms = new FsPermission(APPCACHE_PERM);
    FsPermission fileperms = new FsPermission(FILECACHE_PERM);

    for (String localDir : localDirs) {
      // create $local.dir/usercache/$user/appcache
      Path localDirPath = new Path(localDir);
      final Path appDir = getAppcacheDir(localDirPath, user);
      try {
        createDir(appDir, appCachePerms, true, user);
        appcacheDirStatus = true;
      } catch (IOException e) {
        LOG.warn("Unable to create app cache directory : " + appDir, e);
      }
      // create $local.dir/usercache/$user/filecache
      final Path distDir = getFileCacheDir(localDirPath, user);
      try {
        createDir(distDir, fileperms, true, user);
        distributedCacheDirStatus = true;
      } catch (IOException e) {
        LOG.warn("Unable to create file cache directory : " + distDir, e);
      }
    }
    if (!appcacheDirStatus) {
      throw new IOException("Not able to initialize app-cache directories "
          + "in any of the configured local directories for user " + user);
    }
    if (!distributedCacheDirStatus) {
      throw new IOException(
          "Not able to initialize distributed-cache directories "
              + "in any of the configured local directories for user "
              + user);
    }
  }

};