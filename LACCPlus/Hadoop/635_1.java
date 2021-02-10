//,temp,HeartbeatManager.java,252,262,temp,HeartbeatManager.java,241,250
//,3
public class xxx {
  synchronized void stopDecommission(final DatanodeDescriptor node) {
    LOG.info("Stopping decommissioning of {} node {}",
        node.isAlive ? "live" : "dead", node);
    if (!node.isAlive) {
      node.stopDecommission();
    } else {
      stats.subtract(node);
      node.stopDecommission();
      stats.add(node);
    }
  }

};