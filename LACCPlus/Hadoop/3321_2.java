//,temp,NamenodeBeanMetrics.java,418,427,temp,NamenodeBeanMetrics.java,408,416
//,2
public class xxx {
  @Override
  public String getClusterId() {
    try {
      return getNamespaceInfo(FederationNamespaceInfo::getClusterId).toString();
    } catch (IOException e) {
      LOG.error("Cannot fetch cluster ID metrics {}", e.getMessage());
      return "";
    }
  }

};