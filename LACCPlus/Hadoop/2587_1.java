//,temp,TestDataNodeInitStorage.java,65,70,temp,BlockReportLeaseManager.java,217,225
//,3
public class xxx {
    public SimulatedFsDatasetVerifier(DataStorage storage, Configuration conf) {
      super(storage, conf);
      LOG.info("Assigned DatanodeUuid is " + storage.getDatanodeUuid());
      assert(storage.getDatanodeUuid() != null);
      assert(storage.getDatanodeUuid().length() != 0);
    }

};