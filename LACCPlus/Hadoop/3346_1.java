//,temp,LeveldbTimelineStore.java,1618,1634,temp,LeveldbTimelineStateStore.java,402,418
//,2
public class xxx {
  private void checkVersion() throws IOException {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded timeline store version info " + loadedVersion);
    if (loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing timeline store version info " + getCurrentVersion());
      dbStoreVersion(CURRENT_VERSION_INFO);
    } else {
      String incompatibleMessage = 
          "Incompatible version for timeline store: expecting version " 
              + getCurrentVersion() + ", but loading version " + loadedVersion;
      LOG.error(incompatibleMessage);
      throw new IOException(incompatibleMessage);
    }
  }

};