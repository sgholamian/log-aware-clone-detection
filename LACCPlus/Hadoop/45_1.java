//,temp,TestNNHandlesCombinedBlockReport.java,34,40,temp,TestNNHandlesBlockReportPerStorage.java,35,46
//,3
public class xxx {
  @Override
  protected void sendBlockReports(DatanodeRegistration dnR, String poolId,
                                  StorageBlockReport[] reports) throws IOException {
    LOG.info("Sending combined block reports for " + dnR);
    cluster.getNameNodeRpc().blockReport(dnR, poolId, reports,
        new BlockReportContext(1, 0, System.nanoTime(), 0L));
  }

};