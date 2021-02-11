//,temp,NMLeveldbStateStoreService.java,1020,1034,temp,HistoryServerLeveldbStateStoreService.java,355,369
//,2
public class xxx {
  private void checkVersion() throws IOException {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded NM state version info " + loadedVersion);
    if (loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing NM state version info " + getCurrentVersion());
      storeVersion();
    } else {
      throw new IOException(
        "Incompatible version for NM state: expecting NM state version " 
            + getCurrentVersion() + ", but loading version " + loadedVersion);
    }
  }

};