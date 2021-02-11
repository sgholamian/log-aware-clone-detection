//,temp,DynamicResourceConfiguration.java,114,119,temp,DynamicResourceConfiguration.java,101,105
//,2
public class xxx {
  public void setOverCommitTimeoutPerNode(String node, int overCommitTimeout) {
    setInt(getNodePrefix(node) + OVERCOMMIT_TIMEOUT, overCommitTimeout);
    LOG.debug("DRConf - setOverCommitTimeoutPerNode: nodePrefix=" +
      getNodePrefix(node) +
        ", overCommitTimeout=" + overCommitTimeout);
  }

};