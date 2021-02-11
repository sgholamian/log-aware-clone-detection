//,temp,OzoneManager.java,371,405,temp,StorageContainerManager.java,377,407
//,3
public class xxx {
  public static boolean scmInit(OzoneConfiguration conf) throws IOException {
    SCMStorage scmStorage = new SCMStorage(conf);
    StorageState state = scmStorage.getState();
    if (state != StorageState.INITIALIZED) {
      try {
        String clusterId = StartupOption.INIT.getClusterId();
        if (clusterId != null && !clusterId.isEmpty()) {
          scmStorage.setClusterId(clusterId);
        }
        scmStorage.initialize();
        System.out.println(
            "SCM initialization succeeded."
                + "Current cluster id for sd="
                + scmStorage.getStorageDir()
                + ";cid="
                + scmStorage.getClusterID());
        return true;
      } catch (IOException ioe) {
        LOG.error("Could not initialize SCM version file", ioe);
        return false;
      }
    } else {
      System.out.println(
          "SCM already initialized. Reusing existing"
              + " cluster id for sd="
              + scmStorage.getStorageDir()
              + ";cid="
              + scmStorage.getClusterID());
      return true;
    }
  }

};