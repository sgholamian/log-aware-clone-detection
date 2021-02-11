//,temp,SlowPeerTracker.java,193,203,temp,SlowDiskTracker.java,262,273
//,3
public class xxx {
  public String getSlowDiskReportAsJsonString() {
    try {
      if (slowDisksReport.isEmpty()) {
        return null;
      }
      return WRITER.writeValueAsString(slowDisksReport);
    } catch (JsonProcessingException e) {
      // Failed to serialize. Don't log the exception call stack.
      LOG.debug("Failed to serialize statistics" + e);
      return null;
    }
  }

};