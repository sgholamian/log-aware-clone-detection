//,temp,FileSystemTimelineWriter.java,1019,1031,temp,TimelineCollector.java,217,225
//,3
public class xxx {
    private Path getAppRootDir(String user) throws IOException {
      if (!storeInsideUserDir) {
        return activePath;
      }
      Path userDir = new Path(activePath, user);
      if (FileSystem.mkdirs(fs, userDir,
          new FsPermission(APP_LOG_DIR_PERMISSIONS))) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("New user directory created - " + userDir);
        }
      }
      return userDir;
    }

};