//,temp,NamenodeBeanMetrics.java,408,416,temp,FederationMetrics.java,557,567
//,3
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