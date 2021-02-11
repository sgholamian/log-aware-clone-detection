//,temp,StateStoreFileBaseImpl.java,173,192,temp,StateStoreFileBaseImpl.java,148,171
//,3
public class xxx {
  @Override
  public boolean initDriver() {
    String rootDir = getRootDir();
    try {
      if (rootDir == null) {
        LOG.error("Invalid root directory, unable to initialize driver.");
        return false;
      }

      // Check root path
      if (!exists(rootDir)) {
        if (!mkdir(rootDir)) {
          LOG.error("Cannot create State Store root directory {}", rootDir);
          return false;
        }
      }
    } catch (Exception ex) {
      LOG.error(
          "Cannot initialize filesystem using root directory {}", rootDir, ex);
      return false;
    }
    setInitialized(true);
    return true;
  }

};