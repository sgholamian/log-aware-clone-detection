//,temp,NetworkTopology.java,213,232,temp,NetworkTopologyWithNodeGroup.java,228,254
//,3
public class xxx {
  public void remove(Node node) {
    if (node==null) return;
    if( node instanceof InnerNode ) {
      throw new IllegalArgumentException(
        "Not allow to remove an inner node: "+NodeBase.getPath(node));
    }
    LOG.info("Removing a node: "+NodeBase.getPath(node));
    netlock.writeLock().lock();
    try {
      if (clusterMap.remove(node)) {
        InnerNode rack = (InnerNode)getNode(node.getNetworkLocation());
        if (rack == null) {
          numOfRacks--;
        }
      }
      LOG.debug("NetworkTopology became:\n{}", this);
    } finally {
      netlock.writeLock().unlock();
    }
  }

};