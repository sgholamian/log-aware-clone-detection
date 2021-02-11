//,temp,FileSystemTimelineWriter.java,1007,1017,temp,KMSACLs.java,287,302
//,3
public class xxx {
    private Path createApplicationDir(ApplicationId appId) throws IOException {
      Path appRootDir = getAppRootDir(authUgi.getShortUserName());
      Path appDir = new Path(appRootDir, appId.toString());
      if (FileSystem.mkdirs(fs, appDir,
          new FsPermission(APP_LOG_DIR_PERMISSIONS))) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("New app directory created - " + appDir);
        }
      }
      return appDir;
    }

};