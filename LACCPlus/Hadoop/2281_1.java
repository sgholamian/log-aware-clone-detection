//,temp,YarnConfigurationStore.java,156,176,temp,ShuffleHandler.java,699,713
//,3
public class xxx {
  public void checkVersion() throws Exception {
    // TODO this was taken from RMStateStore. Should probably refactor
    Version loadedVersion = getConfStoreVersion();
    LOG.info("Loaded configuration store version info " + loadedVersion);
    if (loadedVersion != null && loadedVersion.equals(getCurrentVersion())) {
      return;
    }
    // if there is no version info, treat it as CURRENT_VERSION_INFO;
    if (loadedVersion == null) {
      loadedVersion = getCurrentVersion();
    }
    if (loadedVersion.isCompatibleTo(getCurrentVersion())) {
      LOG.info("Storing configuration store version info "
          + getCurrentVersion());
      storeVersion();
    } else {
      throw new RMStateVersionIncompatibleException(
          "Expecting configuration store version " + getCurrentVersion()
              + ", but loading version " + loadedVersion);
    }
  }

};