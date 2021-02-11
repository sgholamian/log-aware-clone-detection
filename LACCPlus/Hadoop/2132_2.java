//,temp,ZKFailoverController.java,279,295,temp,ZKFailoverController_before_log_fix.java,278,294
//,2
public class xxx {
  private boolean confirmFormat() {
    String parentZnode = getParentZnode();
    System.err.println(
        "===============================================\n" +
        "The configured parent znode " + parentZnode + " already exists.\n" +
        "Are you sure you want to clear all failover information from\n" +
        "ZooKeeper?\n" +
        "WARNING: Before proceeding, ensure that all HDFS services and\n" +
        "failover controllers are stopped!\n" +
        "===============================================");
    try {
      return ToolRunner.confirmPrompt("Proceed formatting " + parentZnode + "?");
    } catch (IOException e) {
      LOG.debug("Failed to confirm", e);
      return false;
    }
  }

};