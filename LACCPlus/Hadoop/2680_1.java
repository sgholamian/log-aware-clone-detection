//,temp,NamenodeBeanMetrics.java,418,427,temp,FederationMetrics.java,557,567
//,3
public class xxx {
  @Override
  public String getBlockPoolId() {
    try {
      return
          getNamespaceInfo(FederationNamespaceInfo::getBlockPoolId).toString();
    } catch (IOException e) {
      LOG.error("Cannot fetch block pool ID metrics {}", e.getMessage());
      return "";
    }
  }

};