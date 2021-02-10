//,temp,DynamicResourceConfiguration.java,114,119,temp,DynamicResourceConfiguration.java,88,92
//,2
public class xxx {
  public void setOverCommitTimeoutPerNode(String node, int overCommitTimeout) {
    setInt(getNodePrefix(node) + OVERCOMMIT_TIMEOUT, overCommitTimeout);
    LOG.debug("DRConf - setOverCommitTimeoutPerNode: nodePrefix=" +
      getNodePrefix(node) +
        ", overCommitTimeout=" + overCommitTimeout);
  }

};