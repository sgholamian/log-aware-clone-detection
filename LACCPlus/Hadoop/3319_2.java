//,temp,DynamicResourceConfiguration.java,114,119,temp,DynamicResourceConfiguration.java,101,105
//,2
public class xxx {
  public void setMemoryPerNode(String node, int memory) {
    setInt(getNodePrefix(node) + MEMORY, memory);
    LOG.debug("DRConf - setMemoryPerNode: nodePrefix=" + getNodePrefix(node) +
      ", memory=" + memory);
  }

};