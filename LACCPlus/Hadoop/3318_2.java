//,temp,DynamicResourceConfiguration.java,114,119,temp,DynamicResourceConfiguration.java,88,92
//,2
public class xxx {
  public void setVcoresPerNode(String node, int vcores) {
    setInt(getNodePrefix(node) + VCORES, vcores);
    LOG.debug("DRConf - setVcoresPerNode: nodePrefix=" + getNodePrefix(node) +
      ", vcores=" + vcores);
  }

};