//,temp,NamenodeBeanMetrics.java,354,362,temp,DFSClientCache.java,324,332
//,3
public class xxx {
  private String getNodes(final DatanodeReportType type) {
    try {
      return this.dnCache.get(type);
    } catch (ExecutionException e) {
      LOG.error("Cannot get the DN storage report for {}", type, e);
    }
    // If we cannot get the report, return empty JSON
    return "{}";
  }

};