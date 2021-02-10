//,temp,SlowPeerTracker.java,193,203,temp,SlowDiskTracker.java,262,273
//,3
public class xxx {
  public String getJson() {
    Collection<ReportForJson> validReports = getJsonReports(
        MAX_NODES_TO_REPORT);
    try {
      return WRITER.writeValueAsString(validReports);
    } catch (JsonProcessingException e) {
      // Failed to serialize. Don't log the exception call stack.
      LOG.debug("Failed to serialize statistics" + e);
      return null;
    }
  }

};