//,temp,NMLeveldbStateStoreService.java,1020,1034,temp,HistoryServerLeveldbStateStoreService.java,355,369
//,2
public class xxx {
  private void checkVersion() throws IOException {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded state version info " + loadedVersion);
    if (loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing state version info " + getCurrentVersion());
      storeVersion();
    } else {
      throw new IOException(
        "Incompatible version for state: expecting state version "
            + getCurrentVersion() + ", but loading version " + loadedVersion);
    }
  }

};