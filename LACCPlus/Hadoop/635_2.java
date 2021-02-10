//,temp,HeartbeatManager.java,252,262,temp,HeartbeatManager.java,241,250
//,3
public class xxx {
  synchronized void startDecommission(final DatanodeDescriptor node) {
    if (!node.isAlive) {
      LOG.info("Dead node {} is decommissioned immediately.", node);
      node.setDecommissioned();
    } else {
      stats.subtract(node);
      node.startDecommission();
      stats.add(node);
    }
  }

};