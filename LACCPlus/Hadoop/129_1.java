//,temp,RMStateStore.java,636,654,temp,HistoryServerLeveldbStateStoreService.java,355,369
//,3
public class xxx {
  public void checkVersion() throws Exception {
    Version loadedVersion = loadVersion();
    LOG.info("Loaded RM state version info " + loadedVersion);
    if (loadedVersion != null && loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    // if there is no version info, treat it as CURRENT_VERSION_INFO;
    if (loadedVersion == null) {
      loadedVersion = getCurrentVersion();
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing RM state version info " + getCurrentVersion());
      storeVersion();
    } else {
      throw new RMStateVersionIncompatibleException(
        "Expecting RM state version " + getCurrentVersion()
            + ", but loading version " + loadedVersion);
    }
  }

};