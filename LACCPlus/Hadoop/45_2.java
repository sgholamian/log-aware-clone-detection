//,temp,TestNNHandlesCombinedBlockReport.java,34,40,temp,TestNNHandlesBlockReportPerStorage.java,35,46
//,3
public class xxx {
  @Override
  protected void sendBlockReports(DatanodeRegistration dnR, String poolId,
      StorageBlockReport[] reports) throws IOException {
    int i = 0;
    for (StorageBlockReport report : reports) {
      LOG.info("Sending block report for storage " + report.getStorage().getStorageID());
      StorageBlockReport[] singletonReport = { report };
      cluster.getNameNodeRpc().blockReport(dnR, poolId, singletonReport,
          new BlockReportContext(reports.length, i, System.nanoTime(), 0L));
      i++;
    }
  }

};