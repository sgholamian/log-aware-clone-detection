//,temp,LeveldbTimelineStore.java,1590,1606,temp,LeveldbTimelineStateStore.java,402,418
//,2
public class xxx {
  private void checkVersion() throws IOException {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded timeline state store version info " + loadedVersion);
    if (loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing timeline state store version info " + getCurrentVersion());
      storeVersion(CURRENT_VERSION_INFO);
    } else {
      String incompatibleMessage =
          "Incompatible version for timeline state store: expecting version "
              + getCurrentVersion() + ", but loading version " + loadedVersion;
      LOG.fatal(incompatibleMessage);
      throw new IOException(incompatibleMessage);
    }
  }

};