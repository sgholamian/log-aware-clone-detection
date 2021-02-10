//,temp,OzoneManager.java,371,405,temp,StorageContainerManager.java,377,407
//,3
public class xxx {
  private static boolean omInit(OzoneConfiguration conf) throws IOException {
    OMStorage omStorage = new OMStorage(conf);
    StorageState state = omStorage.getState();
    if (state != StorageState.INITIALIZED) {
      try {
        ScmBlockLocationProtocol scmBlockClient = getScmBlockClient(conf);
        ScmInfo scmInfo = scmBlockClient.getScmInfo();
        String clusterId = scmInfo.getClusterId();
        String scmId = scmInfo.getScmId();
        if (clusterId == null || clusterId.isEmpty()) {
          throw new IOException("Invalid Cluster ID");
        }
        if (scmId == null || scmId.isEmpty()) {
          throw new IOException("Invalid SCM ID");
        }
        omStorage.setClusterId(clusterId);
        omStorage.setScmId(scmId);
        omStorage.initialize();
        System.out.println(
            "OM initialization succeeded.Current cluster id for sd="
                + omStorage.getStorageDir() + ";cid=" + omStorage
                .getClusterID());
        return true;
      } catch (IOException ioe) {
        LOG.error("Could not initialize OM version file", ioe);
        return false;
      }
    } else {
      System.out.println(
          "OM already initialized.Reusing existing cluster id for sd="
              + omStorage.getStorageDir() + ";cid=" + omStorage
              .getClusterID());
      return true;
    }
  }

};