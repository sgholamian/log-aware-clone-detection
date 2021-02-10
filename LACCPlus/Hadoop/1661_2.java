//,temp,LeveldbTimelineStore.java,1590,1606,temp,RollingLevelDBTimelineStore.java,1579,1595
//,3
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
      String incompatibleMessage = "Incompatible version for timeline store: "
          + "expecting version " + getCurrentVersion()
          + ", but loading version " + loadedVersion;
      LOG.fatal(incompatibleMessage);
      throw new IOException(incompatibleMessage);
    }
  }

};