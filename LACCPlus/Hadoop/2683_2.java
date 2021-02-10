//,temp,NamenodeBeanMetrics.java,408,416,temp,FederationMetrics.java,557,567
//,3
public class xxx {
  @Override
  public String getBlockPoolId() {
    try {
      Collection<String> blockpoolIds =
          getNamespaceInfo(FederationNamespaceInfo::getBlockPoolId);
      return blockpoolIds.toString();
    } catch (IOException e) {
      LOG.error("Cannot fetch block pool ID metrics: {}", e.getMessage());
      return "";
    }
  }

};