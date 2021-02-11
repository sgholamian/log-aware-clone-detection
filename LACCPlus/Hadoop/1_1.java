//,temp,ShuffleHandler.java,541,555,temp,RollingLevelDBTimelineStore.java,1579,1595
//,3
public class xxx {
  private void checkVersion() throws IOException {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded state DB schema version info " + loadedVersion);
    if (loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing state DB schedma version info " + getCurrentVersion());
      storeVersion();
    } else {
      throw new IOException(
        "Incompatible version for state DB schema: expecting DB schema version " 
            + getCurrentVersion() + ", but loading version " + loadedVersion);
    }
  }

};