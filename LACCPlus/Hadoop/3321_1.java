//,temp,NamenodeBeanMetrics.java,418,427,temp,NamenodeBeanMetrics.java,408,416
//,2
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